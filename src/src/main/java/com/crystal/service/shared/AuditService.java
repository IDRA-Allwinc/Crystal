package com.crystal.service.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.shared.SelectList;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface AuditService {
    void upsert(Long id, ModelAndView modelView);
    List<SelectList> getSupervisoryEntities(String supervisoryStr);
    Object getAuditsByRole(GridService gridService);
    void fillAudit(Long id, ModelAndView modelView);
    void doObsolete(Long id, Long userId, ResponseMessage response);
}
