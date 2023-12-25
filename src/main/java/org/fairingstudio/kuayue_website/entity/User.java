package org.fairingstudio.kuayue_website.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("ky_user")
public class User {

    private Integer id;

    @TableField("username")
    private String username;

    private String password;

    private Integer role;

    @TableField("sign_up_time")
    private Date signUpTime;

    private String email;

    private String avatar;

    private String sign;

    @TableField("update_time")
    private Date updateTime;

    private String nickname;
}
