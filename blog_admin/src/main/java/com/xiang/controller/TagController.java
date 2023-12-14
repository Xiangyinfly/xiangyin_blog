package com.xiang.controller;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.TagDto;
import com.xiang.domain.entity.Tag;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.service.TagService;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;
//    @GetMapping("/list")
//    public ResponseResult list(){
//        return ResponseResult.okResult(tagService.list());
//    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagDto tagDto){
        return tagService.pageTagList(pageNum,pageSize, tagDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id) {
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable("id") Long id) {
        return tagService.getTag(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        return tagService.updateTag(tag);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }


}
