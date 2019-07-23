package com.wzz.blog.service;

import com.wzz.blog.dao.UserRepository;
import com.wzz.blog.pojo.User;
import com.wzz.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wzzap
 * @date 2019/6/22
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Util.MD5EncodeUtf8WithSalt(password));
    }
}
