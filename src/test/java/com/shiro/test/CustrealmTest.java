/**
 * 
 */
package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import com.imooc.realm.CustomRealm;

/**
 * @author acer
 *
 */
public class CustrealmTest {
    @Test
    public void testAuthenticationTest(){
        
        
        CustomRealm custrealm = new CustomRealm();
        //构建SecurityManager环境
        DefaultSecurityManager defuaultSecurityManger = new DefaultSecurityManager();
        defuaultSecurityManger.setRealm(custrealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defuaultSecurityManger);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermissions("user:delete","user:add");
    }
}
