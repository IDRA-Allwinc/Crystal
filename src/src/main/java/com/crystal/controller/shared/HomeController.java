package com.crystal.controller.shared;

import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.MainPageService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    SharedLogExceptionService logException;

    @Autowired
    MainPageService mainPageService;

    @Autowired
    SharedUserService sharedUserService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView index(ModelMap map){
        ModelAndView model = new ModelAndView("/shared/body");

        try{
            return mainPageService.generatePage("", model, -1l);

        }catch (Exception ex){
            logException.Write(ex, this.getClass(), "index", sharedUserService);
            return model;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody ModelAndView body(HttpServletResponse response){
        return new ModelAndView("/index");
    }
}
