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

    public List<SelectList> findAreasByRole(SharedUserService sharedUserService, String areaStr);

    public boolean findByNumber(RequestDto requestDto, ResponseMessage responseMessage);

    void upsertViewDocs(Long requestId, ModelAndView modelAndView);

    public void attention(Long id, ModelAndView modelView);

    public void doAttention(AttentionDto attentionDto, ResponseMessage responseMessage);

    void doDeleteUpFile(Long requestId, Long upfileId, ResponseMessage response);

    public void doSave(Request request);

    public void save(RequestDto requestDto, AttentionDto attentionDto, ResponseMessage responseMessage);

    public void extension(Long requestId, ModelAndView modelAndView);

    public void doDeleteExtension(Long requestId, Long extensionId, ResponseMessage response);
}
