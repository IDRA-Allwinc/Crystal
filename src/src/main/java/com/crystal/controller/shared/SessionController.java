package com.crystal.controller.shared;

import com.crystal.infrastructure.config.RequestInterceptor;
import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.shared.Constants;
import com.crystal.service.account.SharedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class SessionController {

    @Autowired
    SharedUserService sharedUserService;

    @RequestMapping(value = "/session/checkout", method = RequestMethod.POST)
    public ResponseMessage checkout() {

        ResponseMessage responseMessage = new ResponseMessage();
        String usrName = sharedUserService.getLoggedUsername();

        //si la sesion ha caducado
        if (usrName.equals(Constants.anonymousUser))
            responseMessage.setHasToLogout(true);

        Long lastUserRequestTime = Constants.accessMap.get(usrName);


        if (lastUserRequestTime != null && !usrName.equals(Constants.anonymousUser)) {
            Calendar now = Calendar.getInstance();
            Long elapsedTime = now.getTimeInMillis() - lastUserRequestTime;

            if (elapsedTime >= Long.parseLong(Constants.SystemSettings.Map.get(Constants.SystemSettings.TOTAL_SESSION_LIMIT_TIME_KEY))) {
                responseMessage.setHasToLogout(true);
            } else if (elapsedTime >= Long.parseLong(Constants.SystemSettings.Map.get(Constants.SystemSettings.LIMIT_TIME_KEY))) {
                responseMessage.setHasError(false);
                responseMessage.setReturnData(elapsedTime);
            } else {
                responseMessage.setHasError(false);
                responseMessage.setReturnData(-1);
            }
        }



        return responseMessage;
    }

    @RequestMapping(value = "/session/extend", method = RequestMethod.POST)
    public ResponseMessage extend() {

        ResponseMessage responseMessage = new ResponseMessage();

        String usrName = sharedUserService.getLoggedUsername();
        if (!usrName.equals(Constants.anonymousUser)) {
            RequestInterceptor.updateAccessMap(usrName, Calendar.getInstance().getTimeInMillis());
            responseMessage.setHasError(false);
            responseMessage.setReturnData(true);
        }

        return responseMessage;
    }
}
