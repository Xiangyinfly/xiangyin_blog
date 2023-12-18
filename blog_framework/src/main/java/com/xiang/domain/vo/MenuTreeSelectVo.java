package com.xiang.domain.vo;

import com.xiang.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectVo {
    private Long id;
    private Long parentId;
    private String label;
    private List<MenuTreeSelectVo> children;

}
