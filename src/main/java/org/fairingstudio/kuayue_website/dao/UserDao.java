package org.fairingstudio.kuayue_website.dao;

import org.fairingstudio.kuayue_website.entity.User;

public interface UserDao {

    User login(String username, String password);

    User findUserByName(String username);
}
