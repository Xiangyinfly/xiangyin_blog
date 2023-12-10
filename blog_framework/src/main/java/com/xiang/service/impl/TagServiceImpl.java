package com.xiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.domain.entity.Tag;
import com.xiang.service.TagService;
import com.xiang.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author chenwentao
* @description 针对表【xy_tag(标签)】的数据库操作Service实现
* @createDate 2023-12-10 21:25:50
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




