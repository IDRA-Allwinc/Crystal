package com.crystal.controller.shared;

import com.crystal.service.shared.MainPageService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    //@Autowired
    //private LoginService service;

    @Autowired
    SharedLogExceptionService logException;

    @Autowired
    MainPageService mainPageService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView index(ModelMap map){
        ModelAndView model = new ModelAndView("/index");

        try{
            //Long userId = sharedUserService.GetLoggedUserId();
            return mainPageService.generatePage("", model, -1l);

            /*if(userId == null || userId < 1)
                return mainPageService.generatePage(Constants.ROLE_ANONYMOUS, model, userId);

            List<String> lstRoles = sharedUserService.getLstRolesByUserId(userId);

            if(lstRoles == null || lstRoles.size() < 1)
                return mainPageService.generatePage(Constants.ROLE_ANONYMOUS, model, userId);
            */

            //return mainPageService.generatePage(lstRoles.get(0), model, userId);

        }catch (Exception ex){
            logException.Write(ex, this.getClass(), "index", null);
            return model;
        }
    }


}
