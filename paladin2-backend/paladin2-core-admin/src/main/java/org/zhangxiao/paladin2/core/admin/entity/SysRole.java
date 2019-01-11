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


@TableName("`sys_role`")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    //字段名称映射
    public static final String FN_id = "id";
    public static final String FN_title = "title";
    public static final String FN_des = "des";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField("title")
    private String title;

    /**
     * 描述
     */
    @TableField("des")
    private String des;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
