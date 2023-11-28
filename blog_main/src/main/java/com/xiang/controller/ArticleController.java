package com.xiang.controller;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Article;
import com.xiang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        return articleService.getHotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        return articleService.getArticleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult articleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

}
