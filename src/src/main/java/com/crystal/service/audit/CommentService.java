package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import org.springframework.web.servlet.ModelAndView;

public interface CommentService {
    public void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(CommentDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long commentId, ModelAndView modelAndView);

    void showAttention(Long commentId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    void doDeleteUpFile(Long commentId, Long upFileId, ResponseMessage response);

    void showReplication(Long commentId, ModelAndView modelAndView);

    void doReplication(AttentionDto attentionDto, ResponseMessage response);
}
