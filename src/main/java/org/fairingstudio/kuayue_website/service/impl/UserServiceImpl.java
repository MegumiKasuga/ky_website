package org.fairingstudio.kuayue_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.fairingstudio.kuayue_website.dao.UserDao;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.service.UserService;
import org.fairingstudio.kuayue_website.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

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
    @Transactional
    public int updateLoginInfo(String username, String latestIpAddress, Date latestLoginTime) {

        int nums = userDao.updateLoginInfo(username, latestIpAddress, latestLoginTime);
        return nums;
    }

    @Override
    public Integer getCountByUsername(String username) {

        Integer count = userDao.countByUsername(username);
        return count;
    }

    @Override
    @Transactional
    public int addUser(User user) {
        //设置用户角色为普通用户
        user.setRole("2");
        //初始积分为5
        user.setScore(5);
        Date currentDate = new Date(System.currentTimeMillis());
        user.setSignUpTime(currentDate);

        int nums = userDao.insertUser(user);
        return nums;
    }

    @Override
    public boolean getCountByIpAddress(String ipAddress) {

        Integer nums = userDao.countByIpAddress(ipAddress);
        return nums > 1;
    }

    @Override
    public User checkUser(String username, String password) {

        String tripleSaltCode = MD5Utils.tripleSaltCode(password, "kuayue");
        User userByName = getUserByName(username);
        if (!userByName.getPassword().equals(tripleSaltCode)) {
            return null;
        }
        return userByName;
    }
}
