package com.crystal.service.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public AjaxAuthenticationSuccessHandler() {
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)throws IOException, ServletException {
        String contentType = request.getHeader("Content-Type");
        //if ("application/json".equals(contentType) || "application/x-www-form-urlencoded".equals(contentType)) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        response.getWriter().print("{\"hasError\":false,\"message\":\"\",\"urlToGo\":\"index.html\"}");
        response.getWriter().flush();

        //    super.onAuthenticationSuccess(request, response, auth);

    }
}
