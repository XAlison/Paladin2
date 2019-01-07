package org.zhangxiao.paladin2.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 
 * </p>
 *
 */
@Getter
@Setter


@TableName("`sys_role_permission`")
public class SysRolePermission extends Model<SysRolePermission> {

    private static final long serialVersionUID = 1L;

    //字段名称映射
    public static final String FN_roleId = "role_id";
    public static final String FN_permission = "permission";

    @TableId(value = "role_id", type = IdType.NONE)
    private Long roleId;

    @TableField("permission")
    private byte[] permission;


    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
