package com.xiang.mapper;

import com.xiang.domain.entity.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【xy_link(友链)】的数据库操作Mapper
* @createDate 2023-11-30 08:21:23
* @Entity com.xiang.domain.entity.Link
*/

@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




