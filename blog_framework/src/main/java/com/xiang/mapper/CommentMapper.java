package com.xiang.mapper;

import com.xiang.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【xy_comment(评论表)】的数据库操作Mapper
* @createDate 2023-12-05 17:16:42
* @Entity com.xiang.domain/entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




