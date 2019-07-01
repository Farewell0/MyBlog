package com.wzz.blog.dao;

import com.wzz.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wzzap
 * @date 2019/6/22
 **/
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名和密码查询数据库user表
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
 }
