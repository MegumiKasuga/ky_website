package org.fairingstudio.kuayue_website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.fairingstudio.kuayue_website.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

    User selectUserByName(String username);
}
