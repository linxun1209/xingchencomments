package com.xingchen.comments.dto;

import lombok.Data;

/**
 * @author xing'chen
 */
@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
