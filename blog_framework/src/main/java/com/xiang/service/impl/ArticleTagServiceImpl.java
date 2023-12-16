package com.xiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.domain.entity.ArticleTag;
import com.xiang.service.ArticleTagService;
import com.xiang.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author chenwentao
* @description 针对表【xy_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-12-15 20:44:42
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




