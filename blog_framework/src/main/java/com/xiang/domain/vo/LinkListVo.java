package com.xiang.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkListVo {

    private String address;
    private String description;
    private Long id;
    private String name;
    private String logo;
}
