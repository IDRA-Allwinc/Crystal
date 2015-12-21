package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.account.PasswordDto;
import com.crystal.model.entities.account.UserDto;
import com.crystal.model.entities.account.UserView;
import com.crystal.model.shared.Constants;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditedEntityRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.account.UserService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    UserService serviceUser;
    @Autowired
    private GridService gridService;

    @RequestMapping(value = "/management/user/index", method = RequestMethod.GET)
    public String index() {
        return "/management/user/index";
    }

    @RequestMapping(value = "/management/user/list", method = RequestMethod.GET/*, params = {"limit", "offset", "sort", "order", "search", "filter"}*/)
    public
    @ResponseBody
    Object list() {
        return gridService.toGrid(UserView.class);
    }

    @RequestMapping(value = "/management/user/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/user/upsert");

        try {
            serviceUser.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/management/user/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid UserDto modelNew, BindingResult result, Model m) {

        ResponseMessage response = new ResponseMessage();

        try {

            if (DtoValidator.isValid(result, response) == false)
                return response;

            serviceUser.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }


    @RequestMapping(value = "/management/user/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(@RequestParam(required = true) Long id) {
        ModelAndView modelView = new ModelAndView("/management/user/changePassword");
        try {
            modelView.addObject("id", id);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "changePassword", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/management/user/doChangePassword", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doChangePassword(@Valid PasswordDto modelNew, BindingResult result, Model m) {

        ResponseMessage response = new ResponseMessage();

        try {

            if (DtoValidator.isValid(result, response) == false)
                return response;

            serviceUser.savePsw(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doChangePassword", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/management/user/doObsolete", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            serviceUser.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

}
