package com.xiang.controller;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddArticleDto;
import com.xiang.domain.entity.Article;
import com.xiang.service.ArticleService;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto){
        return articleService.addArticle(articleDto);
    }
}
