package org.zhangxiao.paladin2.core.admin.entity;

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
@Accessors(chain = true)


@TableName("`sys_admin_role`")
public class SysAdminRole extends Model<SysAdminRole> {

    private static final long serialVersionUID = 1L;

    //字段名称映射
    public static final String FN_adminId = "admin_id";
    public static final String FN_roleId = "role_id";

    @TableId(value = "admin_id", type = IdType.NONE)
    private Long adminId;

    @TableField("role_id")
    private Long roleId;


    @Override
    protected Serializable pkVal() {
        return this.adminId;
    }

}
