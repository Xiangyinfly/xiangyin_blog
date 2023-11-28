package com.xiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiang.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【xy_category(分类表)】的数据库操作Mapper
* @createDate 2023-11-28 17:03:01
* @Entity com.xiang.domain.Category
*/

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




