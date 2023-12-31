package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.AddArticleDto;
import com.xiang.domain.dto.UpdateArticleDto;
import com.xiang.domain.entity.Article;
import com.xiang.domain.entity.ArticleTag;
import com.xiang.domain.entity.Category;
import com.xiang.domain.vo.*;
import com.xiang.mapper.ArticleMapper;
import com.xiang.service.ArticleService;
import com.xiang.service.ArticleTagService;
import com.xiang.service.CategoryService;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Lazy
    //会出现循环依赖！
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult getHotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, SystemConstants.PAGE_SIZE);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        //bean拷贝
//        ArrayList<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo hotArticleVo = new HotArticleVo();
//            BeanUtils.copyProperties(article,hotArticleVo);
//            articleVos.add(hotArticleVo);
//        }
        //封装后的方法
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果前端传入种类，就筛选；不传入就不筛选
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus,SystemConstants.STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

        List<Article> records = page.getRecords();


//        records.stream().map(new Function<Article, Article>() {
//            @Override
//            public Article apply(Article article) {
//                Category category = categoryService.getById(article.getCategoryId());
//                article.setCategoryName(category.getName());
//                return article;
//            }
//        });
        //上述表达式的化简
        records = records.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装到articleListVo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        //封装到pageVo
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        //从redis中获得浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    //加入事务
    @Transactional
    public ResponseResult addArticle(AddArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = articleDto.getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        articleTagService.saveOrUpdateBatchByMultiId(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList_admin(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(title),Article::getTitle,title);
        queryWrapper.like(Objects.nonNull(summary),Article::getSummary,summary);
        queryWrapper.eq(Article::getStatus,SystemConstants.STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getCreateTime);//根据时间降序
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<Article> records = page.getRecords();
        List<AdminArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, AdminArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        List<ArticleTag> articleTags = updateArticleDto.getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();

        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(queryWrapper);

        articleTagService.saveOrUpdateBatchByMultiId(articleTags);
        //这里根据主键进行更新，因为ArticleTag有两个主键，目前指定主键为articleId
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        articleTagService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleDetail_admin(Long id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,id);
        Article article = getOne(queryWrapper);
        AdminArticleDetailsVo adminArticleDetailsVo = BeanCopyUtils.copyBean(article, AdminArticleDetailsVo.class);

        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTags = articleTagService.list(articleTagLambdaQueryWrapper);
        List<Long> tagList = articleTags.stream().map(ArticleTag::getTagId).toList();
        adminArticleDetailsVo.setTags(tagList);

        return ResponseResult.okResult(adminArticleDetailsVo);
    }
}
