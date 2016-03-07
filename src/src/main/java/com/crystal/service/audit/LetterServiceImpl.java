package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.*;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.model.shared.UploadFileGenericDto;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.LetterRepository;
import com.crystal.repository.catalog.RequestRepository;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService {

    @Autowired
    LetterRepository repository;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UploadFileGenericRepository repositoryUf;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SharedUserService sharedUserService;


    @Override
    public void upsert(Long id, ModelAndView modelView) {
        if (id != null) {
            Gson gson = new Gson();
            LetterDto model = repository.findOneDto(id);
            model.setLstFiles(repository.findLetterInitialFileDtoById(id));
            String sJson = gson.toJson(model);
            modelView.addObject("model", sJson);
        }
    }

    @Override
    public void upsertAudit(Long auditId, Long id, ModelAndView modelView) {


        if (auditId != null) {
            Audit a = auditRepository.findByIdAndIsObsolete(auditId, false);
            if (a == null)
                return;
        } else {
            return;
        }

        Gson gson = new Gson();
        LetterDto model = null;
        if (id != null) {
            model = repository.findOneDto(id);
            model.setLstFiles(repository.findLetterInitialFileDtoById(id));
        } else {
            model = new LetterDto();
            model.setAuditId(auditId);
        }

        modelView.addObject("model", gson.toJson(model));

    }

    @Override
    public void save(LetterDto modelNew, ResponseMessage response, Long userId, Long roleId) {

        Letter model = businessValidation(modelNew, response, userId, roleId);

        if (response.isHasError())
            return;

        model.merge(modelNew);
        doSave(model);
    }

    @Override
    @Transactional
    public void doObsolete(Long id, Long userId, ResponseMessage response) {
        Letter model = repository.findByIdAndIsObsolete(id, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El oficio ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar oficio");
            return;
        }

        Long countReq = repository.countReqByLetterId(id);

        if (!countReq.equals(0l)) {
            response.setHasError(true);
            response.setMessage("El oficio no puede ser eliminado porque tiene al menos un requisito");
            response.setTitle("Eliminar oficio");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(userId);
        repository.saveAndFlush(model);

        List<Long> lstFileIds = repository.findAllFilesIdsByLetterId(model.getId());
        if (lstFileIds.size() > 0)
            repositoryUf.setAllFilesObsoleteByIds(lstFileIds);
    }

    @Override
    public Long findFileIdByLetterId(Long id) {
        List<Long> lstFiles = repository.findInitialFilesIdsByLetterId(id);
        return lstFiles.get(0);
    }

    @Transactional
    private void doSave(Letter model) {
        repository.saveAndFlush(model);
    }

    private Letter businessValidation(LetterDto modelNew, ResponseMessage response, Long userId, Long roleId) {

        List<UploadFileGenericDto> lstFiles = modelNew.getLstFiles();

        if (lstFiles == null || lstFiles.size() != 1) {
            response.setHasError(true);
            response.setMessage("Debe existir sólo un archivo para asociarlo al oficio.");
            return null;
        }

        Long fileId = lstFiles.get(0).getId();

        Letter model;

        List<Long> lstFileIds;
        boolean bIsSameFile = false;

        Audit a = null;

        if (modelNew.getAuditId() != null) {
            a = auditRepository.findByIdAndIsObsolete(modelNew.getAuditId(), false);
            if (a == null) {
                response.setHasError(true);
                response.setMessage("La auditor&iacute;a no existe o ya fue eliminada.");
                return null;
            }
        }

        if (modelNew.getId() == null) {
            model = new Letter(roleId);
            model.setInsAudit(userId);
            if (modelNew.isForAudit() && a != null) {
                model.setAudit(a);
            }
        } else {
            model = repository.findByIdAndIsObsolete(modelNew.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("El oficio no existe o ya fue eliminado.");
                return null;
            }

            if (model.getRole().getId() != roleId) {
                response.setHasError(true);
                response.setMessage("El usuario no pertenece al perfil asociado al oficio.");
                return null;
            }

            model.setUpdAudit(userId);
            lstFileIds = repository.findInitialFilesIdsByLetterId(model.getId());
            if (!lstFileIds.contains(fileId)) {   //Si es falso, es un archivo diferente
                if (lstFileIds.size() > 0)
                    repositoryUf.setAllFilesObsoleteByIds(lstFileIds);
            } else {
                bIsSameFile = true;
            }
        }

        if (bIsSameFile)
            return model;

        UploadFileGeneric genericFile = repositoryUf.findById(lstFiles.get(0).getId());

        if (genericFile == null) {
            response.setHasError(true);
            response.setMessage("No existe un archivo válido para el oficio.");
            return null;
        }

        genericFile.setObsolete(false);
        repositoryUf.saveAndFlush(genericFile);

        List<LetterUploadFileGenericRel> lstFilesNew = new ArrayList<>(1);
        LetterUploadFileGenericRel relUpFile = new LetterUploadFileGenericRel();
        relUpFile.setLetter(model);
        relUpFile.setUploadFileGeneric(genericFile);
        lstFilesNew.add(relUpFile);
        model.setLstFiles(lstFilesNew);

        return model;
    }

    @Override
    public void upsertViewDocs(Long letterId, ModelAndView modelAndView) {
        LetterDto model = repository.findOneDto(letterId);
        model.setType(Constants.UploadFile.LETTER);
        model.setDescription("");
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long letterId, Long upFileId, ResponseMessage response) {
        Letter model = repository.findByIdAndIsObsolete(letterId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El oficio ya fue eliminado o no existe en el sistema.");
            response.setTitle("Eliminar documento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar el archivo debido a que el requerimiento ya fue atendido");
            response.setTitle("Eliminar documento");
            return;
        }

        List<LetterUploadFileGenericRel> lstFiles = model.getLstFiles();

        for (int i = lstFiles.size() - 1; i >= 0; i--) {
            UploadFileGeneric file =lstFiles.get(i).getUploadFileGeneric();
            if (file.getId().equals(upFileId)) {
                file.setObsolete(true);
                repositoryUf.save(file);
                lstFiles.remove(i);
            }
        }

        repository.saveAndFlush(model);
    }

    @Override
    public void showAttention(Long id, ModelAndView modelAndView) {
        Gson gson = new Gson();
        AttentionDto model = repository.findAttentionInfoById(id);
        modelAndView.addObject("model", gson.toJson(model));
    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {

        Letter model = attentionValidation(attentionDto, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    private Letter attentionValidation(AttentionDto attentionDto, ResponseMessage response) {

        Letter model = null;

        if (attentionDto.getId() == null) {
            response.setHasError(true);
            response.setMessage("El oficio no existe o ya fue eliminado.");
            return null;
        } else {

            model = repository.findByIdAndIsObsolete(attentionDto.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("El oficio no existe o ya fue eliminado.");
                return null;
            }

            List<Long> requestIds = requestRepository.getRequestIdsNoObsoleteByLetter(attentionDto.getId());
            if (requestIds==null || !(requestIds.size() > 0)) {
                response.setHasError(true);
                response.setMessage("No es posible indicar la atenci&oacute;n del oficio, debe tener registrado al menos un requerimiento.");
                return null;
            }

            Long countUnattendedRequests = requestRepository.countRequestUnattendedInIds(requestIds);
            if (countUnattendedRequests>0) {
                response.setHasError(true);
                response.setMessage("No es posible indicar la atenci&oacute;n del oficio, existen requerimientos que no han sido atendidos.");
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
