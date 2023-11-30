package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Link;
import com.xiang.domain.vo.LinkListVo;
import com.xiang.service.LinkService;
import com.xiang.mapper.LinkMapper;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chenwentao
* @description 针对表【xy_link(友链)】的数据库操作Service实现
* @createDate 2023-11-30 08:21:23
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(links, LinkListVo.class);
        return ResponseResult.okResult(linkListVos);
    }
}




