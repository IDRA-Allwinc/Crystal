package com.crystal.service.account;

import org.springframework.web.servlet.ModelAndView;

public interface UserService {
    void upsert(Long id, ModelAndView modelView);
}
