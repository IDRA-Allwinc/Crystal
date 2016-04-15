package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

public interface CommentService {
    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(CommentDto modelNew, ResponseMessage response) throws ParseException;

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long commentId, ModelAndView modelAndView);

    void showAttention(Long commentId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    void doDeleteUpFile(Long commentId, Long upFileId, ResponseMessage response);

    void showReplication(Long commentId, ModelAndView modelAndView);

    void doReplication(AttentionDto attentionDto, ResponseMessage response) throws ParseException;

    void extension(Long commentId, ModelAndView modelAndView);

    void doDeleteExtension(Long commentId, Long extensionId, ResponseMessage response);

    ResponseMessage refreshExtensionComment(Long commentId, ResponseMessage responseMessage);
}
