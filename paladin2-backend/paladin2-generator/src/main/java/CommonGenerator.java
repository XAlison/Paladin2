import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.io.IOException;

public class CommonGenerator {
    public static void generate(String DB_HOST,String DB_PORT,String DB_NAME,String DB_USERNAME,String DB_PASSWORD,String[] tables,String tablePrefix,String OUT_PATH,String PACKAGE_NAME) throws IOException {
        if (tables.length == 0) {
            tables = null;
        }
        String outPath = getModulePath() + "/../.." + OUT_PATH;

        File outDir = new File(outPath);
        outPath = outDir.getCanonicalPath();
        if (!outDir.exists()) {
            System.out.println("路径不存在：" + outPath);
            return;
        }


        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outPath);
        gc.setFileOverride(true);// 文件不做覆盖
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setIdType(IdType.AUTO);
        gc.setAuthor("听风zx");
        gc.setOpen(false);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("%sService");
        gc.setServiceImplName("%sService");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            public IColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(gc, fieldType);
            }
        });

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8" +
                        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT"
                , DB_HOST, DB_PORT, DB_NAME));
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        // strategy.setTablePrefix("fw_");// 此处可以修改为您的表前缀
        if (tablePrefix != null && tablePrefix.length() > 0) {
            strategy.setTablePrefix(tablePrefix);
        }
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tables); // 需要生成的表
        strategy.setEntityLombokModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setLogicDeleteFieldName("logic_delete");
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_NAME);
        mpg.setPackageInfo(pc);

        //不生成 Controller
        //按照自定义模板生成Entity、Mapper
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        tc.setEntity("/mp_tpl/entity.java");
        tc.setMapper("/mp_tpl/mapper.java");
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
    }

    private static String getModulePath() {
        try {
            String path = CommonGenerator.class.getResource("/").toURI().getPath();
            return new File(path).getParentFile().getParentFile().getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
