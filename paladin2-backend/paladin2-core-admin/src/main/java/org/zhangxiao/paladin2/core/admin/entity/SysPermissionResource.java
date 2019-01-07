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


@TableName("`sys_permission_resource`")
public class SysPermissionResource extends Model<SysPermissionResource> {

    private static final long serialVersionUID = 1L;

    //字段名称映射
    public static final String FN_id = "id";
    public static final String FN_permission = "permission";
    public static final String FN_typeId = "type_id";
    public static final String FN_data = "data";

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限表达式
     */
    @TableField("permission")
    private String permission;

    /**
     * 资源类型:0=导航；1=接口；2=UI路由；3=UI元素
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 资源数据
     */
    @TableField("data")
    private String data;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
