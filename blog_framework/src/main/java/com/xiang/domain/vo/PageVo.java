package com.xiang.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

//分页vo
//分页数据的json结构为rows集合和total，集合里包含具体分页数据
public class PageVo {
    private List rows;
    private Long total;
}
