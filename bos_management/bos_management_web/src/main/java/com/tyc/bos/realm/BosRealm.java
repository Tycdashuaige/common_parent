package com.tyc.bos.realm;

import com.tyc.bos.dao.system.IPermissionDao;
import com.tyc.bos.dao.system.IRoleDao;
import com.tyc.bos.dao.system.IUserDao;
import com.tyc.bos.domain.system.Permission;
import com.tyc.bos.domain.system.Role;
import com.tyc.bos.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;

public class BosRealm extends AuthorizingRealm {

    @Resource
    private IUserDao userDao;

    @Resource
    private IRoleDao roleDao;

    @Resource
    private IPermissionDao permissionDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user.getUsername().equals("admin")) {

            List<Role> roleList = roleDao.findAll();
            for (Role role : roleList) {
                info.addRole(role.getKeyword());
            }
            List<Permission> permissionList = permissionDao.findAll();
            for (Permission permission : permissionList) {
                info.addStringPermission(permission.getKeyword());
            }
        } else {

            List<Role> roleList = roleDao.findByUser(user.getId());
            for (Role role : roleList) {
                info.addRole(role.getKeyword());
            }

            List<Permission> permissionList = permissionDao.findByUser(user.getId());
            for (Permission permission : permissionList) {
                info.addStringPermission(permission.getKeyword());
            }
        }
      /*  info.addStringPermission("area");
        info.addRole("");
        info.addStringPermission("courier:save");*/
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

        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
    }
}
