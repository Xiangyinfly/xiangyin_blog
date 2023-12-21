package com.xiang.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联表
 * @TableName sys_user_role
 */
@TableName(value ="sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    /**
     * 用户ID
     */
    @MppMultiId
    private Long userId;

    /**
     * 角色ID
     */
    @MppMultiId
    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}