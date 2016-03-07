package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.AreaDto;
import com.crystal.model.shared.SelectList;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface AreaService {
    ResponseMessage save(AreaDto areaDto, ResponseMessage responseMessage);

    void upsert(Long id, ModelAndView modelAndView);

    ResponseMessage doObsolete(Long id, ResponseMessage responseMessage);

    List<SelectList> getDGPOPAreas(String areaStr);

    List<SelectList> geAreasByAuditedEntityId(Long auditedEntityId, String areaStr);

    List<SelectList> getSelectedAreasByRequestId(Long requestId);

    List<SelectList> getSelectedAreasByCommentId(Long requestId);

    List<SelectList> getSelectedAreasByRecommendationId(Long requestId);

    List<SelectList> getSelectedAreasByObservationId(Long requestId);

    List<SelectList> getSelectedAreasByResponsibilityId(Long requestId);

    List<SelectList> getAreasWithResponsibleByAuditId(Long auditId);

    List<SelectList> getAreasWithEmailResponsibleByAuditId(Long auditId);

    List<SelectList> getSelectedAreasByNotificationId(Long id);
}
