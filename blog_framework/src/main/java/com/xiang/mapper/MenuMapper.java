package com.xiang.mapper;

import com.xiang.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author chenwentao
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-12-11 20:25:19
* @Entity com.xiang.domain/entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




