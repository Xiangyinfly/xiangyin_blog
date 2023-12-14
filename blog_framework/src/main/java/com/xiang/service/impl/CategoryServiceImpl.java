package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Article;
import com.xiang.domain.entity.Category;
import com.xiang.domain.vo.CategoryListVo;
import com.xiang.domain.vo.CategoryVo;
import com.xiang.service.ArticleService;
import com.xiang.service.CategoryService;
import com.xiang.mapper.CategoryMapper;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author chenwentao
* @description 针对表【xy_category(分类表)】的数据库操作Service实现
* @createDate 2023-11-28 17:03:01
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询状态为正式发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并通过set去重
        Set<Long> idSet = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        List<Category> categories = listByIds(idSet);
        //筛选正常状态的分类
        categories = categories.stream().filter(c -> SystemConstants.STATUS_NORMAL.equals(c.getStatus())).collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.STATUS_NORMAL);
        List<Category> categoryList = list(queryWrapper);
        List<CategoryListVo> categoryVoList = BeanCopyUtils.copyBeanList(categoryList, CategoryListVo.class);
        return ResponseResult.okResult(categoryVoList);
    }
}




