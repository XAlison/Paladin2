package org.zhangxiao.paladin2.core.admin.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.zhangxiao.paladin2.core.admin.AdminProperties;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionResourceService;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * 参考文章
 * 1、https://www.jianshu.com/p/672abf94a857?winzoom=1
 * 2、http://blog.csdn.net/xtiawxf/article/details/52571949
 * 3、https://segmentfault.com/a/1190000014479154
 * 4、添加自定拦截器：https://blog.csdn.net/qq_35981283/article/details/78633692
 * 5、拦截器、过滤器：https://blog.csdn.net/heweimingming/article/details/79993591
 * 6、Shiro+JWT+Spring Boot Restful简易教程：https://juejin.im/post/59f1b2766fb9a0450e755993
 * 7、CredentialsMatcher ：https://blog.csdn.net/u012881904/article/details/53843386
 */
@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     * 不需要Autowired，创建Bean的时候，自动会注入SysPermissionResourceService
     * 在Configuration中Autowired，似乎并没什么卵用
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager
            , SysPermissionResourceService sysPermissionResourceService
            , AdminProperties adminProperties
    ) {
        logger.info("注入Shiro的Web过滤器-->shiroFilter", ShiroFilterFactoryBean.class);

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //region 添加自定义过滤器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        AdminFilter adminFilter = new AdminFilter();
        adminFilter.setSysPermissionResourceService(sysPermissionResourceService, adminProperties);
        filtersMap.put("adminFilter", adminFilter);
        shiroFilterFactoryBean.setFilters(filtersMap);
        //endregion

        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //region 因为前后端进行了分离，所以以下URL均设为null
        //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(null);
        //登录成功后要跳转的连接,逻辑也可以自定义，例如返回上次请求的页面
        shiroFilterFactoryBean.setSuccessUrl(null);
        //用户访问未对其授权的资源时,所显示的连接
        shiroFilterFactoryBean.setUnauthorizedUrl(null);
        //endregion

        //region 定义Shiro过滤器链
        // 执行顺序：从上向下顺序，一旦匹配，就不往下执行了
        // anon：表示匿名访问，所有用户均可以访问
        // adminFilter:针对后台管理员的过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/manage/passport/**", "anon");
        filterChainDefinitionMap.put("/manage/**", "adminFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //endregion

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        logger.info("注入Shiro的Web过滤器-->securityManager", ShiroFilterFactoryBean.class);
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        manager.setRealm(adminRealm());
        return manager;
    }

    /**
     * Shiro Realm 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的
     */
    @Bean
    public AdminRealm adminRealm() {
        return new AdminRealm();
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
