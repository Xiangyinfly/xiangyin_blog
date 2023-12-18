package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chenwentao
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-12-11 20:25:19
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long menuId);

    ResponseResult getTreeSelect();

    ResponseResult getRoleMenuTreeselect(Long id);
}
