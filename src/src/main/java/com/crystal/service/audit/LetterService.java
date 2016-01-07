package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.PasswordDto;
import com.crystal.model.entities.account.UserDto;
import org.springframework.web.servlet.ModelAndView;

public interface LetterService {
    void upsert(Long id, ModelAndView modelView);
    void save(UserDto modelNew, ResponseMessage response);
    void doObsolete(Long id, ResponseMessage response);
}
