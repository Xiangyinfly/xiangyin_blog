package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.constants.SystemConstants;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Menu;
import com.xiang.domain.entity.RoleMenu;
import com.xiang.domain.vo.MenuListVo;
import com.xiang.domain.vo.MenuTreeSelectVo;
import com.xiang.domain.vo.MenuVo;
import com.xiang.domain.vo.RoleMenuTreeselectVo;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import com.xiang.mapper.RoleMenuMapper;
import com.xiang.service.MenuService;
import com.xiang.mapper.MenuMapper;
import com.xiang.service.RoleMenuService;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        } else {
            menuList = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        List<Long> parentIdList = menuList.stream().map(Menu::getParentId).toList();
        //构建tree
        List<Menu> menuTree = buildMenuTree(menuList, Collections.min(parentIdList));
        //List<Menu> menuTree = builderMenuTree(menuList,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getMenuList(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.STATUS_NORMAL.equals(status) || SystemConstants.STATUS_ABNORMAL.equals(status),Menu::getStatus,status);
        queryWrapper.eq(Objects.nonNull(menuName),Menu::getMenuName,menuName);
        queryWrapper.orderByAsc(Menu::getParentId);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menuList, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,id);
        Menu menu = getOne(queryWrapper);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    //如何去完善菜单的更新？如何保证所有属性合理？
    //想到的方法：后端规避太为繁琐，可以前端去限制一些属性的选择，从而规避一些错误，比如父菜单不存在
    public ResponseResult updateMenu(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,menu.getId());
        if (getOne(queryWrapper) == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_SUCH_MENU);
        }
        if (menu.getParentId().equals(menu.getId())) {
            throw new RuntimeException("上级菜单不能选择自己！");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    //menu-role表怎么办？
    //children属性在表中不存在！
    public ResponseResult deleteMenu(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Menu::getId,menuId);
        Menu menu = getOne(queryWrapper1);

        LambdaQueryWrapper<Menu> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Menu::getParentId,menuId);
        List<Menu> menuList = list(queryWrapper2);
        if (menuList != null) {
            throw new RuntimeException("存在子菜单，不允许删除");
        }
//        if (menu.getChildren() != null) {
//            throw new RuntimeException("存在子菜单，不允许删除");
//        }
        removeById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTreeSelect() {
        List<Menu> menuList = list();
        List<MenuTreeSelectVo> menuTreeSelectVos = BeanCopyUtils.copyBeanList(menuList, MenuTreeSelectVo.class);
        menuTreeSelectVos.forEach(m -> m.setLabel(
                menuList.stream().filter(ml -> ml.getId().equals(m.getId())).toList().get(0).getMenuName()
        ));

        List<Long> parentIdList = menuList.stream().map(Menu::getParentId).toList();
        List<MenuTreeSelectVo> menuTree = buildMenuTreeSelect(menuTreeSelectVos, Collections.min(parentIdList));

        return ResponseResult.okResult(menuTree);
    }

    @Override
    public ResponseResult getRoleMenuTreeselect(Long id) {
        List<Menu> menuList = getBaseMapper().getMenu(id);
        List<Long> menuIdList = menuList.stream().map(m -> m.getId()).toList();

        List<MenuTreeSelectVo> menuTreeSelectVos = BeanCopyUtils.copyBeanList(menuList, MenuTreeSelectVo.class);
        menuTreeSelectVos.forEach(m -> m.setLabel(
                menuList.stream().filter(ml -> ml.getId().equals(m.getId())).toList().get(0).getMenuName()
        ));

        List<Long> parentIdList = menuList.stream().map(Menu::getParentId).toList();
        List<MenuTreeSelectVo> menuTree = buildMenuTreeSelect(menuTreeSelectVos, Collections.min(parentIdList));

        RoleMenuTreeselectVo roleMenuTreeselectVo = new RoleMenuTreeselectVo(menuTree, menuIdList);

        return ResponseResult.okResult(roleMenuTreeselectVo);

    }

    //构建树型结构
    //TODO 构建树形结构的问题：
    //1.传入的parentId如何确定。传入的parentId应为树的根结点
    //2.menuList中不存在以menuTree中元素的id为parentId的元素，但依然存在其他元素。此时会返回null，导致递归结束！
    //为什么会出现第二种：菜单列表是一个树形结构，但是非完全筛选可能会形成森林

    //我的解决方法：暂且找到parentId中最小的作为根结点parentId
    private List<Menu> buildMenuTree(List<Menu> menuList, long parentId) {
        List<Menu> menuTree = menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .collect(Collectors.toList());

        menuTree.forEach(menu -> menu.setChildren(buildMenuTree(menuList,menu.getId())));

        return menuTree;
    }

    private List<MenuTreeSelectVo> buildMenuTreeSelect(List<MenuTreeSelectVo> menuList, long parentId) {
        List<MenuTreeSelectVo> menuTree = menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .collect(Collectors.toList());

        menuTree.forEach(menu -> menu.setChildren(buildMenuTreeSelect(menuList,menu.getId())));

        return menuTree;
    }


    //我的解决方法，但是栈溢出
//    private List<Menu> builderMenuTree(List<Menu> menuList, List<Long> parentIdList,int index) {
//        long parentId = parentIdList.get(index);
//        List<Menu> menuTree = menuList.stream()
//                .filter(menu -> menu.getParentId().equals(parentId))
//                .collect(Collectors.toList());
//
//        menuTree.forEach(menu -> menu.setChildren(getChildren(menu, menuList)));
//
//        builderMenuTree(menuTree,parentIdList,index++);
//        return menuTree;
//    }
//
//    private List<Menu> getChildren(Menu menu, List<Menu> menuList) {
//        List<Menu> childrenList = menuList.stream()
//                .filter(m -> m.getParentId().equals(menu.getId()))
//                .collect(Collectors.toList());
//
//        childrenList.forEach(child -> child.setChildren(getChildren(child, menuList)));
//
//        return childrenList;
//    }
//
//    private List<MenuTreeSelectVo> builderMenuTreeSelect(List<MenuTreeSelectVo> menuList, List<Long> parentIdList,int index) {
//        long parentId = parentIdList.get(index);
//        List<MenuTreeSelectVo> menuTree = menuList.stream()
//                .filter(menu -> menu.getParentId().equals(parentId))
//                .collect(Collectors.toList());
//
//        menuTree.forEach(menu -> menu.setChildren(getChildrenSelect(menu, menuList)));
//
//        builderMenuTreeSelect(menuTree,parentIdList,index++);
//        return menuTree;
//    }
//
//    private List<MenuTreeSelectVo> getChildrenSelect(MenuTreeSelectVo menu, List<MenuTreeSelectVo> menuList) {
//        List<MenuTreeSelectVo> childrenList = menuList.stream()
//                .filter(m -> m.getParentId().equals(menu.getId()))
//                .collect(Collectors.toList());
//
//        childrenList.forEach(child -> child.setChildren(getChildrenSelect(child, menuList)));
//
//        return childrenList;
//    }







    //构建树
//    private List<Menu> buildMenuTree(List<Menu> menuList, long parentId) {
//        List<Menu> menuTree = menuList.stream()
//                .filter(menu -> menu.getParentId().equals(parentId))
//                .collect(Collectors.toList());
//
//        menuTree.forEach(menu -> menu.setChildren(getChildren(menu, menuList)));
//
//        return menuTree;
//    }
//
//    private List<Menu> getChildren(Menu menu, List<Menu> menuList) {
//        List<Menu> childrenList = menuList.stream()
//                .filter(m -> m.getParentId().equals(menu.getId()))
//                .collect(Collectors.toList());
//
//        childrenList.forEach(child -> child.setChildren(getChildren(child, menuList)));
//
//        return childrenList;
//    }

}




