package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.TagDto;
import com.xiang.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenwentao
* @description 针对表【xy_tag(标签)】的数据库操作Service
* @createDate 2023-12-10 21:25:50
*/
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult listAllTag();
}
