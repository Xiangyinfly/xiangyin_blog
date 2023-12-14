package com.xiang.domain.dto;

import lombok.Data;

@Data
public class TagDto {
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}