package com.xiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult getHotArticleList();

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}
