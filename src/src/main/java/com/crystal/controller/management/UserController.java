package com.crystal.controller.management;

import com.crystal.model.entities.account.Role;
import com.crystal.model.entities.account.User;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.account.UserRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    UserRepository repositoryUser;
    @Autowired
    RoleRepository repositoryRole;


    @RequestMapping(value = "/management/user/index", method = RequestMethod.GET)
    public String index() {
        return "/management/user/index";
    }


    @RequestMapping(value = "/management/user/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/user/upsert");

        Gson gson = new Gson();
        String lstRoles = gson.toJson(repositoryRole.findSelectList());

        modelView.addObject("lstRoles", lstRoles);

        if (id != null) {
            User model = repositoryUser.findOne(id);
            modelView.addObject("model", model);

            if(model != null){
                Role role = model.getRole();
                if (role != null)
                    modelView.addObject("roleId", role.getId());
            }
        }

        return modelView;
    }
}
