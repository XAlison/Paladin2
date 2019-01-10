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
 * 系统-权限表
 * </p>
 *
 */
@Getter
@Setter


@TableName("`sys_permission`")
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    //字段名称映射
    public static final String FN_permission = "permission";
    public static final String FN_title = "title";
    public static final String FN_parent = "parent";
    public static final String FN_sort = "sort";
    public static final String FN_navPath = "nav_path";

    /**
     * 权限表达式
     */
    @TableId(value = "permission", type = IdType.NONE)
    private String permission;

    /**
     * 中文显示项
     */
    @TableField("title")
    private String title;

    /**
     * 父级权限表达式
     */
    @TableField("parent")
    private String parent;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 导航路径
     */
    @TableField("nav_path")
    private String navPath;


    @Override
    protected Serializable pkVal() {
        return this.permission;
    }

}
