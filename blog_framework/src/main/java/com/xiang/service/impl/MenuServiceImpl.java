package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.entity.Menu;
import com.xiang.service.MenuService;
import com.xiang.mapper.MenuMapper;
import com.xiang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author chenwentao
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-12-11 20:25:19
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，则返回全部权限
        if (id == 1L) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            //permission中需要有所有菜单类型为C或F的
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            //状态为正常的
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menuList = list(queryWrapper);
            List<String> perms = menuList.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menuList = null;
        if (SecurityUtils.isAdmin()) {
            menuList = menuMapper.selectAllRouterMenu();
        }
        menuList = menuMapper.selectRouterMenuTreeByUserId(userId);

        //构建tree
        List<Menu> menuTree = builderMenuTree(menuList,0L);
        return menuTree;
    }

    //构建树
    private List<Menu> builderMenuTree(List<Menu> menuList, long parentId) {
        List<Menu> menuTree = menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .collect(Collectors.toList());

        menuTree.forEach(menu -> menu.setChildren(getChildren(menu, menuList)));

        return menuTree;
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menuList) {
        List<Menu> childrenList = menuList.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());

        childrenList.forEach(child -> child.setChildren(getChildren(child, menuList)));

        return childrenList;
    }



}




