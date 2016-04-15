package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Request;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.shared.SelectList;
import com.crystal.service.account.SharedUserService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface RequestService {
    void upsert(Long letterId, Long id, ModelAndView modelView);

    void doObsolete(Long id, ResponseMessage response);

    List<SelectList> findAreasByRole(SharedUserService sharedUserService, String areaStr);

    boolean findByNumberAndLetterId(RequestDto requestDto, ResponseMessage responseMessage);

    void upsertViewDocs(Long requestId, ModelAndView modelAndView);

    void attention(Long id, ModelAndView modelView);

    void doAttention(AttentionDto attentionDto, ResponseMessage responseMessage);

    void doDeleteUpFile(Long requestId, Long upfileId, ResponseMessage response);

    void doSave(Request request);

    void save(RequestDto requestDto, AttentionDto attentionDto, ResponseMessage responseMessage);

    void extension(Long requestId, ModelAndView modelAndView);

    void doDeleteExtension(Long requestId, Long extensionId, ResponseMessage response);


    List<SelectList> findPossibleAssistants(String assistantStr);

    ResponseMessage refreshExtensionRequest(Long requestId, ResponseMessage responseMessage);
}
