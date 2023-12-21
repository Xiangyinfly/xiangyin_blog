package com.xiang.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    private Long id;
    private String userName;
    /**
     * 昵称 */
    private String nickName;
    /**
     * 头像 */
    private String sex;
    private String email;

    private String status;

}
