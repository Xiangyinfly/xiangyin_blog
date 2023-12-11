package com.xiang.mapper;

import com.xiang.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【xy_tag(标签)】的数据库操作Mapper
* @createDate 2023-12-10 21:25:50
* @Entity com.xiang.domain/entity.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




