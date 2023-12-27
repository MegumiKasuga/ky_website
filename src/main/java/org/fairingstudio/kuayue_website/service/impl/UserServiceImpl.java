package org.fairingstudio.kuayue_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.fairingstudio.kuayue_website.dao.UserDao;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByName(String name) {

        //配置查询构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", name);
        User user = userDao.selectOne(wrapper);

        //User user = userDao.selectUserByName(name);

        return user;
    }

    @Override
    public int updateLoginInfo(String username, String latestIpAddress, Date latestLoginTime) {

        int nums = userDao.updateLoginInfo(username, latestIpAddress, latestLoginTime);
        return nums;
    }
}
