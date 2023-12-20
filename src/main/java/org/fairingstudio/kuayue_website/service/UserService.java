package org.fairingstudio.kuayue_website.service;

import org.fairingstudio.kuayue_website.entity.User;

public interface UserService {

    //用户登录
    User getUserByName(String name);
}
