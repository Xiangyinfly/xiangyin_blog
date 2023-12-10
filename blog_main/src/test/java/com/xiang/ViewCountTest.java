package com.xiang;

import com.xiang.domain.entity.Article;
import com.xiang.mapper.ArticleMapper;
import com.xiang.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@SpringBootTest
public class ViewCountTest {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;


    @Test
    public void test() {
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articleList
                .stream()
                .collect(
                        Collectors.toMap(
                                article -> article.getId().toString(),
                                article -> article.getViewCount().intValue()
                        )
                );
        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
