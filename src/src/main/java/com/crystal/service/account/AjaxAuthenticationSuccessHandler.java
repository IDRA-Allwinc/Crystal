package com.crystal.service.account;

import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SystemSetting;
import com.crystal.repository.shared.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    SystemSettingRepository systemSettingRepository;

    public AjaxAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(auth);
        response.getWriter().print("{\"hasError\":false,\"message\":\"\",\"urlToGo\":\"index.html\"}");
        response.getWriter().flush();

        //se cargan todos los settings al mapa
        if (Constants.systemSettings.size() == 0) {
            List<SystemSetting> lstSettings = systemSettingRepository.findAll();
            for (SystemSetting setting : lstSettings) {
                Constants.systemSettings.put(setting.getKey(), setting.getValue());
            }
        }

    }
}
