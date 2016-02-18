package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Comment;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.CommentRepository;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AreaService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UploadFileGenericRepository repositoryUf;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    SharedUserService sharedUserService;

    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();

        CommentDto model;
        if (id != null) {
            model = commentRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByCommentId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        } else {
            model = new CommentDto();
            model.setAuditId(auditId);
        }

        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void save(CommentDto modelNew, ResponseMessage response) {

        Comment model = businessValidation(modelNew, null, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    public void doObsolete(Long id, ResponseMessage response) {

        Comment model = commentRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La observaci&oacute;n ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar requerimiento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una observaci&oacute;n que ya ha sido atendida.");
            response.setTitle("Eliminar observaci&oacute;n");
            return;
        }

        if (model.getLstExtension() != null && model.getLstExtension().size() > 0) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una observaci&oacute;n que ya tiene una prorroga.");
            response.setTitle("Eliminar observaci&oacute;n");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());
        //TODO HACER OBSOLETOS LOS ARCHIVOS
        commentRepository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(Comment model) {
        commentRepository.saveAndFlush(model);
    }

    private Comment businessValidation(CommentDto commentDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
        Comment comment = null;

        if (commentDto != null) {
            Long id = commentDto.getId();

            if (id != null) {
                comment = commentRepository.findByIdAndIsObsolete(id, false);
                if (comment == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("La observaci&oacute;n ya fue eliminado o no existe en el sistema.");
                    return null;
                } else if (comment.isAttended() == true) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("No es posible modificar una observaci&oacute;n que ya ha sido atendida.");
                    return null;
                }

                comment.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                comment = new Comment();
                comment.setCreateDate(Calendar.getInstance());
                comment.setInsAudit(sharedUserService.getLoggedUserId());
                try {
                    Calendar init = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    init.setTime(sdf.parse(commentDto.getInitDate()));
                    end.setTime(sdf.parse(commentDto.getEndDate()));
                    comment.setInitDate(init);
                    comment.setEndDate(end);
                } catch (Exception e) {
                    return null;
                }

            }

            List<SelectList> lstSelectedAreas = new Gson().fromJson(commentDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
            }.getType());

            if (comment.getLstAreas() != null) {
                comment.setLstAreas(null);
            }

            List<Area> lstNewSelectedAreas;
            if (lstSelectedAreas != null && lstSelectedAreas.size() > 0) {
                lstNewSelectedAreas = new ArrayList<>();
                for (SelectList item : lstSelectedAreas) {
                    Area a = new Area();
                    a.setId(item.getId());
                    lstNewSelectedAreas.add(a);
                }
            } else {
                responseMessage.setHasError(true);
                responseMessage.setMessage("Debe seleccionar al menos un &aacute;rea.");
                return null;
            }

            comment.merge(commentDto, null, null);
            comment.setLstAreas(lstNewSelectedAreas);
            Audit a = auditRepository.findOne(commentDto.getAuditId());
            comment.setAudit(a);
        }

        if (attentionDto != null) {
            comment = commentRepository.findOne(attentionDto.getId());
            comment.merge(null, attentionDto, userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return comment;
    }

    @Override
    public void upsertViewDocs(Long letterId, ModelAndView modelAndView) {
//        LetterDto model = repository.findOneDto(letterId);
//        model.setDescription("");
//        Gson gson = new Gson();
//        String sModel = gson.toJson(model);
//        modelAndView.addObject("model", sModel);
    }

    @Override
    public void showAttention(Long id, ModelAndView modelAndView) {
//        Gson gson = new Gson();
//        AttentionDto model = repository.findAttentionInfoById(id);
//        modelAndView.addObject("model", gson.toJson(model));
    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {

        Comment model = attentionValidation(attentionDto, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    private Comment attentionValidation(AttentionDto attentionDto, ResponseMessage response) {

        Comment model = null;

        if (attentionDto.getId() == null) {
            response.setHasError(true);
            response.setMessage("La observaci&oacute;n no existe o ya fue eliminada.");
            return null;
        } else {

            model = commentRepository.findByIdAndIsObsolete(attentionDto.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("La observaci&oacute;n no existe o ya fue eliminada.");
                return null;
            }

            model.setAttentionDate(Calendar.getInstance());
            model.setAttentionComment(attentionDto.getAttentionComment());
            model.setAttended(true);
            model.setAttentionUser(userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return model;
    }
}
