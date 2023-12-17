package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Menu;
import com.xiang.service.MenuService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/content/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @SystemLog(businessName = "查询菜单列表")
    @GetMapping("/list")
    public ResponseResult getMenuList(String status,String menuName) {
        return menuService.getMenuList(status,menuName);
    }

    @SystemLog(businessName = "新增菜单")
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);

    }

    @SystemLog(businessName = "根据id查询菜单数据")
    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);

    }
    @SystemLog(businessName = "修改菜单")
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);

    }

    @SystemLog(businessName = "删除菜单")
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable Long menuId) {
        return menuService.deleteMenu(menuId);

    }

}
