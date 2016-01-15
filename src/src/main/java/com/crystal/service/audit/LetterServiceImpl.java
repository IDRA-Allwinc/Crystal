package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.LetterDto;
import com.crystal.model.entities.audit.LetterUploadFileGenericRel;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.model.shared.UploadFileGenericDto;
import com.crystal.repository.catalog.LetterRepository;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService {

    @Autowired
    LetterRepository repository;

    @Autowired
    UploadFileGenericRepository repositoryUf;


    @Override
    public void upsert(Long id, ModelAndView modelView) {
        if (id != null) {
            Gson gson = new Gson();
            LetterDto model = repository.findOneDto(id);
            model.setLstFiles(repository.findFilesById(id));
            String sJson = gson.toJson(model);
            modelView.addObject("model", sJson);
        }
    }

    @Override
    public void save(LetterDto modelNew, ResponseMessage response, Long userId, Long roleId) {

        Letter model = businessValidation(modelNew, response, userId, roleId);

        if(response.isHasError())
            return;

        model.merge(modelNew);
        doSave(model);
    }

    @Override
    @Transactional
    public void doObsolete(Long id, Long userId, ResponseMessage response) {
        Letter model = repository.findByIdAndIsObsolete(id, false);

        if(model == null){
            response.setHasError(true);
            response.setMessage("El oficio ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar oficio");
            return;
        }

        Long countReq = repository.countReqByLetterId(id);

        if(!countReq.equals(0l)){
            response.setHasError(true);
            response.setMessage("El oficio no puede ser eliminado porque tiene al menos un requisito");
            response.setTitle("Eliminar oficio");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(userId);
        repository.saveAndFlush(model);

        List<Long> lstFileIds = repository.findFileIdsById(model.getId());
        if(lstFileIds.size() > 0)
            repositoryUf.setFilesObsoleteByIds(lstFileIds);
    }

    @Override
    public Long findFileIdByLetterId(Long id) {
        List<Long> lstFiles = repository.findFileIdsById(id);
        return lstFiles.get(0);
    }

    @Transactional
    private void doSave(Letter model) {
        repository.saveAndFlush(model);
    }

    private Letter businessValidation(LetterDto modelNew, ResponseMessage response, Long userId, Long roleId) {

        List<UploadFileGenericDto> lstFiles = modelNew.getLstFiles();

        if(lstFiles == null || lstFiles.size() != 1){
            response.setHasError(true);
            response.setMessage("Debe existir sólo un archivo para asociarlo al oficio.");
            return null;
        }

        Long fileId = lstFiles.get(0).getId();

        Letter model;

        List<Long> lstFileIds;
        boolean bIsSameFile = false;
        if(modelNew.getId() == null){
            model = new Letter(roleId);
            model.setInsAudit(userId);
        }else{
            model = repository.findByIdAndIsObsolete(modelNew.getId(), false);
            if(model == null){
                response.setHasError(true);
                response.setMessage("El oficio no existe o ya fue eliminado.");
                return null;
            }

            if(model.getRole().getId() != roleId){
                response.setHasError(true);
                response.setMessage("El usuario no pertenece al perfil asociado al oficio.");
                return null;
            }

            model.setUpdAudit(userId);
            lstFileIds = repository.findFileIdsById(model.getId());
            if(!lstFileIds.contains(fileId)){   //Si es falso, es un archivo diferente
                if(lstFileIds.size() > 0)
                    repositoryUf.setFilesObsoleteByIds(lstFileIds);
            }
            else{
                bIsSameFile = true;
            }
        }

        if(bIsSameFile)
            return model;

        UploadFileGeneric genericFile = repositoryUf.findById(lstFiles.get(0).getId());

        if(genericFile == null){
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
}
