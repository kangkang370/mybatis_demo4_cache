package com.bupt.dao;

import com.bupt.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface IUserDao {
    /**
     * 查询所有用户,通知获取到用户下所有账户信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);



}
