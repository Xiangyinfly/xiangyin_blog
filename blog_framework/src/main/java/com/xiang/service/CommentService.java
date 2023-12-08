package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenwentao
* @description 针对表【xy_comment(评论表)】的数据库操作Service
* @createDate 2023-12-05 17:16:42
*/
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
