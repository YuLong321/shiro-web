/**
 * 
 */
package com.imooc.Dao;

import java.util.List;

import com.imooc.bean.User;

/**
 * @author acer
 *
 */
public interface UserDao {
    public  User getByUserName(String userName);
    public  List<String> getRolesName(String userName);
    public List<String> getperminssion(String userName);
}
