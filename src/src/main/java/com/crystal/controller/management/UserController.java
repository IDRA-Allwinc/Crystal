package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.security.CryptoRfc2898;
import com.crystal.model.entities.account.Role;
import com.crystal.model.entities.account.User;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.account.UserViewRepository;
import com.crystal.service.account.SharedUserService;
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
    UserRepository repositoryUser;
    @Autowired
    RoleRepository repositoryRole;
    @Autowired
    UserViewRepository userViewRepository;


    @RequestMapping(value = "/management/user/index", method = RequestMethod.GET)
    public String index() {
        return "/management/user/index";
    }


    @RequestMapping(value = "/management/user/list", method = RequestMethod.GET/*, params = {"limit", "offset", "sort", "order", "search", "filter"}*/)
    public @ResponseBody Object list() {
        return userViewRepository.toGrid();
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

    @RequestMapping(value = "/management/user/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid User modelNew, BindingResult result, Model m) {

        ResponseMessage response = new ResponseMessage();

        try {
            User model;

            if (modelNew.getId().longValue() > 0L) {
                model = repositoryUser.findOne(modelNew.getId());
                model.setUsername(modelNew.getUsername());
                model.setEmail(modelNew.getEmail());
                model.setFullname(modelNew.getFullname());
                //model.getRoles().clear();
                //model.setRoles(modelNew.getRoles());

                if (modelNew.getHasChangePass()) {
                    model.setPassword(modelNew.getPassword());
                    model.setConfirm(modelNew.getConfirm());
                } else {
                    model.setConfirm(model.getPassword());
                }
            } else {
                model = modelNew;
                model.setEnabled(true);
            }

//            ResponseMessage resp = PojoValidator.validate(model);
//            if (resp != null)
//                return resp;

            CryptoRfc2898 cryptoRfc2898 = new CryptoRfc2898();

            if (model.getId().longValue() <= 0L || modelNew.getHasChangePass())
                model.setPassword(cryptoRfc2898.encode(modelNew.getPassword()));

            Long idUser = repositoryUser.findIdByUsername(model.getUsername());

            if (idUser != null && idUser.equals(model.getId()) == false) {
                response.setHasError(true);
                response.setMessage("El usuario ya existe, por favor elija otro usuario");
                return response;
            }

            repositoryUser.save(model);
            response.setHasError(false);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }
}
