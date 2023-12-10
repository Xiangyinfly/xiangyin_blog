package com.xiang.job;

import com.xiang.domain.entity.Article;
import com.xiang.service.ArticleService;
import com.xiang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 将redis中的访问数量同步到数据库
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount() {
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(
                        entry -> new Article(
                                Long.valueOf(entry.getKey()),
                                entry.getValue().longValue()
                        )
                ).collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }
}
