package org.fairingstudio.kuayue_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Integer id;

    private String userName;

    private String password;

    private Integer role;

    private Date signUpTime;

    private String email;

    private String avatar;

    private Date updateTime;

    private String nickname;
}
