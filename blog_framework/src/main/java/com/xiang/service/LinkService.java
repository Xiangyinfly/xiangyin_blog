package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenwentao
* @description 针对表【xy_link(友链)】的数据库操作Service
* @createDate 2023-11-30 08:21:23
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
