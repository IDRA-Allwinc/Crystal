package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.LetterDto;
import org.springframework.web.servlet.ModelAndView;

public interface LetterService {
    void upsert(Long id, ModelAndView modelView);
    void save(LetterDto modelNew, ResponseMessage response, Long userId, Long roleId);
    void doObsolete(Long id, Long userId, ResponseMessage response);
    Long findFileIdByLetterId(Long id);
}
