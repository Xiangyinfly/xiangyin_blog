package com.xiang.runner;

import com.xiang.domain.entity.Article;
import com.xiang.mapper.ArticleMapper;
import com.xiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在启动时把博客的浏览量存储在redis中
 */

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articleList
                .stream()
                .collect(
                        Collectors.toMap(
                                article -> article.getId().toString(),
                                article -> article.getViewCount().intValue()
                        )
                );
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
