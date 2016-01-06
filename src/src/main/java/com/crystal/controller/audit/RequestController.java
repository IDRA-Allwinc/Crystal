package com.crystal.controller.audit;

import com.crystal.model.entities.audit.view.RequestView;
import com.crystal.service.shared.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 1/5/2016.
 */
@Controller
public class RequestController {

    @Autowired
    GridService gridService;

    @RequestMapping(value = "/audit/request/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/request/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/request/list", method = RequestMethod.GET)
    public Object list() {
        return gridService.toGrid(RequestView.class);
    }
}
