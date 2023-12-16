package com.xiang.mapper;

import com.xiang.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【xy_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-12-15 20:44:42
* @Entity com.xiang.domain.entity.ArticleTag
*/
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}




