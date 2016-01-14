package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.shared.SelectList;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface RequestService {
    void upsert(Long letterId, Long id, ModelAndView modelView);

    void save(RequestDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    public List<SelectList> findAreasByRole(SharedUserService sharedUserService, String areaStr);

    public boolean findByNumber(RequestDto requestDto, ResponseMessage responseMessage);

    void upsertViewDocs(Long requestId, ModelAndView modelAndView);

    public void attention(Long id, ModelAndView modelView);

    public void doAttention(AttentionDto attentionDto, ResponseMessage responseMessage);

    void doDeleteUpFile(Long requestId, Long upfileId, ResponseMessage response);
}
