package com.xiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.xiang.domain.entity.RoleMenu;
import com.xiang.service.RoleMenuService;
import com.xiang.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author chenwentao
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-12-18 14:39:11
*/
@Service
public class RoleMenuServiceImpl extends MppServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




