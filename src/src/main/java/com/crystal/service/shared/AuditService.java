package com.crystal.service.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.dto.AuditDto;
import com.crystal.model.shared.SelectList;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

public interface AuditService {
    void upsert(Long id, ModelAndView modelView, boolean isModal);

    List<SelectList> getSupervisoryEntities(String supervisoryStr);

    List<SelectList> getAuditedEntities(String auditedStr);

    Object getAuditsByRole(GridService gridService);

    void fillAudit(Long id, ModelAndView modelView);

    void doObsolete(Long id, Long userId, ResponseMessage response);

    void save(AuditDto auditDto, ResponseMessage responseMessage, HttpServletRequest request) throws ParseException;

    void doSave(Audit audit);

    ModelAndView getInfoDetail(Long auditId, String type, ModelAndView model);
}
