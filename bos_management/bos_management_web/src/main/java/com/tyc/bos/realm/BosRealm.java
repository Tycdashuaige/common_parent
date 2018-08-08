package com.tyc.bos.realm;

import com.tyc.bos.dao.system.IUserDao;
import com.tyc.bos.domain.system.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class BosRealm extends AuthorizingRealm {

    @Resource
    private IUserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("area");
        info.addRole("");
        info.addStringPermission("courier:save");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();

        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }

        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
    }
}
