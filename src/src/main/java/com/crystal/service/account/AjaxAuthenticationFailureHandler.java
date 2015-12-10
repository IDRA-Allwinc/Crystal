package com.crystal.service.account;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public AjaxAuthenticationFailureHandler() {
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)throws IOException, ServletException {
        String contentType = request.getHeader("Content-Type");
        // if ("application/json".equals(contentType) || "application/x-www-form-urlencoded".equals(contentType)) {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("{\"hasError\":\"true\",\"message\":\"Usuario y/o contraseña no son válidos. Revise y vuelva a intentar\",\"urlToGo\":\"\"}");
        response.getWriter().flush();
        // } else {
        //super.onAuthenticationFailure(request, response, exception);
        //}
    }
}
