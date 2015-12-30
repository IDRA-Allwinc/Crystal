package com.crystal.service.shared;

import com.crystal.model.shared.LogGetRequest;
import com.crystal.model.shared.LogPostRequest;
import com.crystal.repository.shared.LogGetRequestRepository;
import com.crystal.repository.shared.LogPostRequestRepository;
import com.crystal.service.account.SharedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Enumeration;

@Service
public class LogRequestServiceImpl implements LogRequestService {

    @Autowired
    LogPostRequestRepository logPostRequestRepository;

    @Autowired
    LogGetRequestRepository logGetRequestRepository;

    @Autowired
    SharedLogExceptionService sharedLogExceptionService;

    @Autowired
    SharedUserService sharedUserService;

    public void saveRequest(HttpServletRequest request) {

        String username = sharedUserService.GetLoggedUsername();

        try {
            if (request.getMethod().equals("POST")) {
                logPostRequestRepository.save(this.createLogPostRequest(request));
            } else if (request.getMethod().equals("GET")) {
                logGetRequestRepository.save(this.createLogGetRequest(request));
            }
        } catch (Exception e) {
            sharedLogExceptionService.Write(e, this.getClass(), "saveRequest", sharedUserService);
        }

    }

    private LogPostRequest createLogPostRequest(HttpServletRequest request) throws Exception {
        LogPostRequest lpr;
        try {
            lpr = new LogPostRequest();
            lpr.setUsername(sharedUserService.GetLoggedUsername());
            lpr.setHostAddress(request.getRemoteAddr());
            lpr.setResource(request.getRequestURI());
            lpr.setRegisterDate(Calendar.getInstance());
            lpr.setRequestedData(this.getParamsDataInJson(request));
            lpr.setHeaderData(this.getHeaderInfoInJson(request));
        } catch (Exception e) {
            throw new Exception("Error al crear el objeto LogPostRequest");
        }
        return lpr;
    }

    private LogGetRequest createLogGetRequest(HttpServletRequest request) throws Exception {
        LogGetRequest lgr;
        try {
            lgr = new LogGetRequest();
            lgr.setUsername(sharedUserService.GetLoggedUsername());
            lgr.setHostAddress(request.getRemoteAddr());
            lgr.setResource(request.getRequestURI());
            lgr.setRegisterDate(Calendar.getInstance());
            lgr.setRequestedData(this.getParamsDataInJson(request));
            lgr.setHeaderData(this.getHeaderInfoInJson(request));
        } catch (Exception e) {
            throw new Exception("Error al crear el objeto LogPostRequest");
        }
        return lgr;
    }


    private String getParamsDataInJson(HttpServletRequest request) throws Exception {
        Enumeration<String> paramNames = request.getParameterNames();
        String finalStr = null, line;

        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            line = "\"" + name + "\"" + ":" + "\"" + value + "\"";
            finalStr = finalStr != null ? finalStr + "," + line : line;
        }

        finalStr = "{" + finalStr + "}";

        return finalStr;
    }

    private String getHeaderInfoInJson(HttpServletRequest request) throws Exception {
        Enumeration<String> headerNames = request.getHeaderNames();
        String finalStr = null, line;

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            line = "\"" + name + "\"" + ":" + "\"" + value + "\"";
            finalStr = finalStr != null ? finalStr + "," + line : line;
        }

        finalStr = "{" + finalStr + "}";

        return finalStr;
    }

}
