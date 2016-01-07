package com.crystal.controller.audit;

import com.crystal.model.entities.audit.view.RequestView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 1/5/2016.
 */
@RestController
public class RequestController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;


    @RequestMapping(value = "/audit/request/list", method = RequestMethod.GET)
    public Object requestList(@RequestParam(required = true)Long idLetter) {
        return gridService.toGrid(RequestView.class, "idLetter", idLetter);
    }

}