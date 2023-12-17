package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddArticleDto;
import com.xiang.domain.dto.UpdateArticleDto;
import com.xiang.service.ArticleService;
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

    @SystemLog(businessName = "获取文章列表")
    @GetMapping("/list")
    public ResponseResult getArticleList(Integer pageNum,Integer pageSize,String title,String summary) {
        return articleService.getArticleList_admin(pageNum,pageSize,title,summary);
    }

    @SystemLog(businessName = "查询文章详情")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetails(@PathVariable Long id) {
        return articleService.getArticleDetail_admin(id);
    }

    @SystemLog(businessName = "更新文章")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @SystemLog(businessName = "删除文章")
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }

}
