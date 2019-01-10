package org.zhangxiao.paladin2.core.admin.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.StrUtils;
import org.zhangxiao.paladin2.core.admin.AdminBizError;

import java.util.Date;
import java.util.Optional;

public class AdminToken implements AuthenticationToken {

    private static final Logger log = LoggerFactory.getLogger(AdminToken.class);
    private static final String ID_CLAIM_NAME = "admin_id";
    private static final String SUBJECT_NAME = "admin";

    @Setter
    private Boolean isAuthenticated = false;
    @Setter
    @Getter
    private String jwtStr;
    @Setter
    private Long adminId = null;


    public static AdminToken parse(String jwtStr, String secret) throws BizException {
        if (StrUtils.isEmpty(jwtStr)) {
            log.info("jwt解析失败-jwt为空");
            throw new BizException(AdminBizError.ADMIN_TOKEN_CREATE_FAILURE);
        }
        Algorithm algorithm;
        try {
            algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(jwtStr);
            AdminToken adminToken = new AdminToken();
            adminToken.setAdminId(Optional.ofNullable(jwt.getClaim(ID_CLAIM_NAME)).map(Claim::asLong).orElse(null));
            adminToken.setIsAuthenticated(true);
            adminToken.setJwtStr(jwtStr);
            return adminToken;
        } catch (Exception e) {
            log.info("jwt解析失败-jwt验证失败");
            throw new BizException(AdminBizError.ADMIN_TOKEN_CREATE_FAILURE);
        }
    }

    public static AdminToken create(Long adminId, String secret) throws BizException {
        try {
            //明文标准数据
            JWTCreator.Builder builder = JWT.create()
                    .withSubject(SUBJECT_NAME)
                    .withIssuedAt(new Date());
            //明文自定义数据
            builder.withClaim(ID_CLAIM_NAME, adminId);
            String jwtStr = builder.sign(Algorithm.HMAC256(secret));
            AdminToken adminToken = new AdminToken();
            adminToken.setAdminId(adminId);
            adminToken.setIsAuthenticated(true);
            adminToken.setJwtStr(jwtStr);
            return adminToken;
        } catch (Exception e) {
            throw new BizException(AdminBizError.ADMIN_TOKEN_CREATE_FAILURE);
        }
    }

    public static void main(String[] args) throws BizException {
        //构建user-token用于测试
        AdminToken token = AdminToken.create(1L, "admin@jwt@secret2019-1-10 10:13:03");
        System.out.println(token);
        AdminToken adminToken = AdminToken.parse(token.getJwtStr(), "admin@jwt@secret2019-1-10 10:13:03");
        System.out.println(adminToken.isAuthenticated());
        if (adminToken.isAuthenticated()) {
            System.out.println(adminToken.getPrincipal());
            System.out.println(adminToken.getCredentials());
        }
    }

    @Override
    public Object getPrincipal() {
        return adminId;
    }

    @Override
    public Object getCredentials() {
        return jwtStr;
    }

    public Boolean isAuthenticated() {
        return isAuthenticated;
    }
}
