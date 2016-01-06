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

        String usrName = sharedUserService.GetLoggedUsername();
        Long lastUserRequestTime = Constants.accessMap.get(usrName);

        if (!Constants.excludedUrls.contains(request.getRequestURI()) && !usrName.equals(Constants.anonymousUser)) {
            logRequestService.saveRequest(request);
            if (lastUserRequestTime != null) {
                this.updateAccessMap(usrName, Calendar.getInstance().getTimeInMillis());
                System.out.println("existe y se actualiza --->>" + usrName);
            } else if (lastUserRequestTime == null) {
                Constants.accessMap.put(usrName, Calendar.getInstance().getTimeInMillis());
                System.out.println("no existe se inserta --->>" + usrName);
            }

            return true;
        }

        return true;
    }

    public static final void updateAccessMap(String userName, Long time){
        Constants.accessMap.remove(userName);
        Constants.accessMap.put(userName, time);
    }


}