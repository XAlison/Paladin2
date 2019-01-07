package org.zhangxiao.paladin2.core.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统管理员
 * </p>
 *
 */
@Getter
@Setter


@TableName("`sys_admin`")
public class SysAdmin extends Model<SysAdmin> {

    private static final long serialVersionUID = 1L;

    //字段名称映射
    public static final String FN_id = "id";
    public static final String FN_account = "account";
    public static final String FN_password = "password";
    public static final String FN_nickName = "nick_name";
    public static final String FN_mobile = "mobile";
    public static final String FN_createTime = "create_time";
    public static final String FN_updateTime = "update_time";
    public static final String FN_loginTime = "login_time";
    public static final String FN_loginCount = "login_count";
    public static final String FN_remark = "remark";
    public static final String FN_status = "status";

    /**
     * 账号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 联系电话
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
