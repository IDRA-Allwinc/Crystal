package com.crystal.controller.audit;

import com.crystal.model.entities.audit.view.LetterView;
import com.crystal.model.entities.audit.view.RequestView;
import com.crystal.service.shared.GridService;
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

    @RequestMapping(value = "/audit/letter/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/request/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/letter/list", method = RequestMethod.GET)
    public Object letterList() {
        return gridService.toGrid(LetterView.class);
    }

    @RequestMapping(value = "/audit/request/list", method = RequestMethod.GET)
    public Object requestList(@RequestParam(required = true)Long idLetter) {
        return gridService.toGrid(RequestView.class, "idLetter", idLetter);
    }
}