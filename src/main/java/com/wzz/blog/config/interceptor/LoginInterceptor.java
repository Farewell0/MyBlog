package com.wzz.blog.config.interceptor;

import com.wzz.blog.pojo.User;
import com.wzz.blog.utils.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wzzap
 * @date 2019/6/22
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if(user == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
