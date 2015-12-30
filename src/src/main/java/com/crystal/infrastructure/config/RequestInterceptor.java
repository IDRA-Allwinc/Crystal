package com.crystal.infrastructure.config;

import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.LogRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    SharedUserService sharedUserService;

    @Autowired
    LogRequestService logRequestService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logRequestService.saveRequest(request);
        return true;
    }

}