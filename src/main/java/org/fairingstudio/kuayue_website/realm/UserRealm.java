package org.fairingstudio.kuayue_website.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //自定义登录认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获取用户身份信息（用户名）
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //调用业务层对象获取用户信息（数据库表）
        User user = userService.getUserByName(username);
        //非空判断用户名是否存在，用户名不存在则返回空值抛出异常
        if (user == null) {
            return null;
        }
        //用户名存在，将数据完成封装返回
        AuthenticationInfo info = new SimpleAuthenticationInfo(
                user,
                user.getPassword(), //用户密码
                ByteSource.Util.bytes("kuayue"), //带盐加密字符串
                getName() //当前realm对象的名称
        );
        return info;
    }
}

/*
System.out.println("authenticationToken = " + authenticationToken.toString());
authenticationToken = org.apache.shiro.authc.UsernamePasswordToken - admin, rememberMe=false

System.out.println("info = " + info);
info = User(id=1, username=admin, password=6ebb9158b017a65479570e1ae3da00e3,
role=1, signUpTime=Sun Dec 10 13:01:57 CST 2023, email=null, avatar=null,
updateTime=Sun Dec 17 13:02:07 CST 2023, nickname=管理员)
*/
