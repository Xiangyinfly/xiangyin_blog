package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.TagDto;
import com.xiang.domain.entity.LoginUser;
import com.xiang.domain.entity.Tag;
import com.xiang.domain.vo.PageVo;
import com.xiang.domain.vo.TagListVo;
import com.xiang.domain.vo.TagVo;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import com.xiang.service.TagService;
import com.xiang.mapper.TagMapper;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
* @author chenwentao
* @description 针对表【xy_tag(标签)】的数据库操作Service实现
* @createDate 2023-12-10 21:25:50
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(tagDto.getName()),Tag::getName, tagDto.getName());
        queryWrapper.eq(Objects.nonNull(tagDto.getRemark()),Tag::getRemark, tagDto.getRemark());
        Page<Tag> tagPage = new Page<>();
        tagPage.setCurrent(pageNum);
        tagPage.setSize(pageSize);
        page(tagPage, queryWrapper);
        PageVo pageVo = new PageVo(tagPage.getRecords(), tagPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        if (!StringUtils.hasText(tag.getName())) {
            throw new SystemException(AppHttpCodeEnum.NAME_NOT_NULL);
        }
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,tag.getId());
        if (getOne(queryWrapper) != null) {
            throw new SystemException(AppHttpCodeEnum.TAG_ALREADY_EXIST);
        }

        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        boolean flag = removeById(id);
        if (!flag) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_TAG);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,id);
        Tag tag = getOne(queryWrapper);
        if (tag == null) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_TAG);
        }
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }
    //看一下之前怎么写的get

    @Override
    public ResponseResult updateTag(Tag tag) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,tag.getId());
        if (getOne(queryWrapper) == null) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_TAG);
        }
        updateById(tag);
        return ResponseResult.okResult();
    }    //如果tag不存在怎么判断


    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> tagList = list(queryWrapper);
        List<TagListVo> tagVoList = BeanCopyUtils.copyBeanList(tagList, TagListVo.class);
        return ResponseResult.okResult(tagVoList);
    }

}




