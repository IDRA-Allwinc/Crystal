package com.crystal.service.account;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.UserDto;
import org.springframework.web.servlet.ModelAndView;

public interface UserService {
    void upsert(Long id, ModelAndView modelView);
    void save(UserDto modelNew, ResponseMessage response);
}
