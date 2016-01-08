package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.LetterDto;
import com.crystal.repository.catalog.LetterRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Service
public class LetterServiceImpl implements LetterService {

    @Autowired
    LetterRepository repository;


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
    public void save(LetterDto modelNew, ResponseMessage response) {

        Letter model = businessValidation(modelNew, response);

        if(response.isHasError())
            return;

        model.merge(modelNew);
        doSave(model);
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        Letter model = repository.findByIdAndIsObsolete(id, true);

        if(model == null){
            response.setHasError(true);
            response.setMessage("El oficio ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar oficio");
            return;
        }

        model.setObsolete(true);
        repository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(Letter model) {
        repository.saveAndFlush(model);
    }

    private Letter businessValidation(LetterDto modelNew, ResponseMessage response) {
/*        if (modelNew.getId() == null) {

            if(modelNew.getPsw() == null){
                response.setHasError(true);
                response.setMessage("Se debe definir la contraseña del usuario.");
                return null;
            }

            if (existUsername(modelNew.getUsername())) {
                response.setHasError(true);
                response.setMessage("El usuario ya ha sido utilizado.");
                return null;
            }
        } else {
            if (existUsernameWithNotId(modelNew.getUsername(), modelNew.getId())) {
                response.setHasError(true);
                response.setMessage("El usuario ya ha sido utilizado.");
                return null;
            }
        }

        String roleCode = repositoryRole.findCodeById(modelNew.getRoleId());

        if(roleCode == null || (roleCode.equals(Constants.ROLE_LINK) && modelNew.getAuditedEntityId() == null)){
            response.setHasError(true);
            response.setMessage("Debe elegir un organismo cuando el usuario tiene el perfil de enlace.");
            return null;
        }

        modelNew.setRoleCode(roleCode);

        User model;
        if(modelNew.getId() == null){
            model = new User();
        }else{
            model = repositoryUser.findByIdAndEnabled(modelNew.getId(), true);
        }

        if(model == null){
            response.setHasError(true);
            response.setMessage("El usuario no existe o ya fue eliminado.");
            return null;
        }

        return model;*/
        return null;
    }
}
