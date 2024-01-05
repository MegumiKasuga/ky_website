package org.fairingstudio.kuayue_website.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("ky_user")
//实体类必须实现序列化接口，不然无法创建cookie对象
public class User implements Serializable {

    private Integer id;

    @TableField("username")
    private String username;

    private String password;

    private String role;

    @TableField("sign_up_time")
    private Date signUpTime;

    private String email;

    private String avatar;

    private String sign;

    @TableField("update_time")
    private Date updateTime;

    private String nickname;

    @TableField("latest_ip_address")
    private String latestIpAddress;

    @TableField("latest_login_time")
    private Date latestLoginTime;

    @TableField("sign_up_ip_address")
    private String signUpIpAddress;

    private Integer score;
}
