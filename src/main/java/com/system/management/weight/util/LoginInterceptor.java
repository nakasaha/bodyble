package com.system.management.weight.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
	  @Override
	    public boolean preHandle(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            Object handler) throws Exception {

	        HttpSession session = request.getSession(false);
	        if (session != null && session.getAttribute("loginUser") != null) {
	            // ログイン済み
	            return true;
	        }
	        // 未ログイン → ログイン画面へ
	        response.sendRedirect("/login");
	        return false;
	    }
}
