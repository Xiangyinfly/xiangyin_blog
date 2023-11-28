package com.xiang.controller;


import com.xiang.domain.ResponseResult;
import com.xiang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping ("/getCategoryList")
    public ResponseResult getCategoryList () {
        return categoryService.getCategoryList() ;
    }

}
