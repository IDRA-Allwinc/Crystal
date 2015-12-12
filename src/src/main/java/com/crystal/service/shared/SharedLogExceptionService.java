package com.crystal.service.shared;

import com.crystal.model.shared.LogException;
import com.crystal.repository.shared.LogExceptionRepository;
import com.crystal.service.account.SharedUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SharedLogExceptionService {

    @Autowired
    LogExceptionRepository logExceptionRepository;


    public void Write(Exception ex, Class<?> cClass, String sMethod, SharedUserService sharedUserService) {
        try {
            String username = sharedUserService.GetLoggedUsername();
            logExceptionRepository.save(new LogException(cClass.getName(), sMethod, ex.getMessage() + " | " + ex.getStackTrace(), username));
        }catch (Exception exIn){
            WriteToFile(ex, cClass, sMethod, exIn);
        }
    }


    public void Write(Exception ex, Class<?> cClass, String sMethod, String username) {
        try {
            logExceptionRepository.save(new LogException(cClass.getName(), sMethod, ex.getMessage() + " | " + ex.getStackTrace(), username));
        }catch (Exception exIn){
            WriteToFile(ex, cClass, sMethod, exIn);
        }
    }

    private void WriteToFile(Exception ex, Class<?> cClass, String sMethod, Exception exIn) {
        try{
            Logger logger = Logger.getLogger(cClass);
            logger.error(sMethod + " | " + ex.getMessage() + " | " + ex.getStackTrace() + " | " + exIn.getMessage() + " | " + exIn.getStackTrace());
        }catch (Exception exw){
            return;
        }


    }
}
