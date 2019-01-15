import java.io.IOException;

/**
 * <p>
 * 代码生成器
 * </p>
 */
public class MybatisPlusGenerator {
    // 数据库相关设置
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "paladin2";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    // 指定生成的目录（以整个工程的根目录为准）
    private static final String OUT_PATH = "/paladin2-core-admin/src/main/java";
    // 指定生成的目录（以整个工程的根目录为准）
    private static final String PACKAGE_NAME = "org.zhangxiao.paladin2.core.admin";
    // 指定需要生成的表名（没有指定，则全部生成）
    private static String[] tables = new String[]{
            "sys_admin",
            // "sys_admin_role",
            // "sys_permission",
            // "sys_permission_resource",
            // "sys_role",
            // "sys_role_permission",
    };

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) throws IOException {

        CommonGenerator.generate(
                DB_HOST, DB_PORT, DB_NAME
                , DB_USERNAME, DB_PASSWORD, tables, null
                , OUT_PATH, PACKAGE_NAME
        );
    }

}