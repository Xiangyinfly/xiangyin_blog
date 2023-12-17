package com.xiang.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminArticleListVo {
    private Long id;
    private String content;
    private Long categoryId;
    private String isComment;
    private String isTop;
    private String status;
    private String summary;
    private String thumbnail;
    private String title;
    private Long viewCount;
    private Date createTime;

}
