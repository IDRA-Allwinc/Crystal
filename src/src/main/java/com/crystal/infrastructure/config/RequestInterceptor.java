package com.crystal.infrastructure.config;

import com.crystal.model.shared.Constants;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.LogRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    SharedUserService sharedUserService;

    @Autowired
    LogRequestService logRequestService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String usrName = sharedUserService.getLoggedUsername();
        Long lastUserRequestTime = Constants.accessMap.get(usrName);

        System.out.println();

        if (!request.getRequestURI().equals(request.getContextPath() + Constants.sessionCheckoutUrl) && !request.getRequestURI().equals(request.getContextPath() + Constants.sessionExtendUrl) && !usrName.equals(Constants.anonymousUser)) {
            logRequestService.saveRequest(request);
            if (lastUserRequestTime != null) {
                this.updateAccessMap(usrName, Calendar.getInstance().getTimeInMillis());
            } else if (lastUserRequestTime == null) {
                Constants.accessMap.put(usrName, Calendar.getInstance().getTimeInMillis());
            }

            return true;
        }

        return true;
    }

    public static final void updateAccessMap(String userName, Long time) {
        Constants.accessMap.remove(userName);
        Constants.accessMap.put(userName, time);
    }


}