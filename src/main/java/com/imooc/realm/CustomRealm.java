/**
 * 
 */
package com.imooc.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.imooc.Dao.UserDao;
import com.imooc.bean.User;

/**
 * @author acer
 *
 */
public class CustomRealm  extends AuthorizingRealm{
    @Resource
    private UserDao userDao;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles =getRolesName(username); 
        Set<String> perminssion = getPerminssion(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(perminssion);
        return simpleAuthorizationInfo;
    }

    /**
     * @param username
     * @return
     */
    private Set<String> getRolesName(String username) {
      List<String> list= userDao.getRolesName(username);
      Set<String> roles = new HashSet<String>(list);
        return roles;
    }

    /**
     * @param username 
     * @return
     */
    private Set<String> getPerminssion(String username) {
        
        
       List<String> list =userDao.getperminssion(username);
       Set<String> perminssions = new HashSet<String>(list);
        return perminssions;
    }


    //验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //从主体传过来的认证信息中,获取用户名
        String username = (String) authenticationToken.getPrincipal();
        //通过用户名到数据库获取凭证
        String password=getByUserNamePassword(username);
        if(password==null){
            return null;
        } 
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password,"customRealm");
        return authenticationInfo;
    }

    
  
   /**
     * @param username
     * @return
     */
    private String getByUserNamePassword(String username) {
       User user = userDao.getByUserName(username);
       if(user !=null){
           return user.getPassword();
       }
        return null;
    }

public static void main(String[] args) {
    Md5Hash md5hash = new Md5Hash("123456");
    System.out.println(md5hash.toString());
}
}
