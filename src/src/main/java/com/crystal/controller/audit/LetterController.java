package com.crystal.controller.audit;

import com.crystal.model.entities.audit.view.LetterView;
import com.crystal.model.entities.audit.view.RequestView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.LetterService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 1/5/2016.
 */
@RestController
public class LetterController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    LetterService serviceLetter;

    @RequestMapping(value = "/audit/letter/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/request/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/letter/list", method = RequestMethod.GET)
    public Object letterList() {
        return gridService.toGrid(LetterView.class);
    }

    @RequestMapping(value = "/audit/letter/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/audit/letter/upsert");

        try {
            serviceLetter.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

}