package org.fairingstudio.kuayue_website.service;

import org.fairingstudio.kuayue_website.entity.User;

import java.util.Date;

public interface UserService {

    //用户登录
    User getUserByName(String name);

    int updateLoginInfo(String username, String latestIpAddress, Date latestLoginTime);

    Integer getCountByUsername(String username);

    int addUser(User user);
}
