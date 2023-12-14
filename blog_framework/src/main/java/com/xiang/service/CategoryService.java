package com.xiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Category;

/**
* @author chenwentao
* @description 针对表【xy_category(分类表)】的数据库操作Service
* @createDate 2023-11-28 17:03:01
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();
}
