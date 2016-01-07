package com.crystal.service.audit;

import com.crystal.infrastructure.extensions.StringExt;
import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.PasswordDto;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.account.UserDto;
import com.crystal.model.entities.audit.Letter;
import com.crystal.model.shared.Constants;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditedEntityRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Service
public class LetterServiceImpl implements LetterService {

    @Autowired
    UserRepository repositoryUser;
    @Autowired
    RoleRepository repositoryRole;
    @Autowired
    AuditedEntityRepository auditedEntityRepository;


    @Override
    public void upsert(Long id, ModelAndView modelView) {
        Gson gson = new Gson();
        String sJson = gson.toJson(repositoryRole.findSelectList());
        modelView.addObject("lstRoles", sJson);
        sJson = gson.toJson(auditedEntityRepository.findSelectList(Constants.ENTITY_TYPE_INDEPENDENT_BODY));
        modelView.addObject("lstAuditedEntities", sJson);

        if (id != null) {
            UserDto model = repositoryUser.findOneDto(id);
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setPassword("********");
            passwordDto.setConfirm("********");
            model.setPsw(passwordDto);
            sJson = gson.toJson(model);
            modelView.addObject("model", sJson);
        }
    }

    @Override
    public void save(UserDto modelNew, ResponseMessage response) {

        User model = businessValidation(modelNew, response);

        if(response.isHasError())
            return;

        model.merge(modelNew);
        doSave(model);
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        User model = repositoryUser.findByIdAndEnabled(id, true);

        if(model == null){
            response.setHasError(true);
            response.setMessage("El usuario ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar usuario");
            return;
        }

        model.setUsername(StringExt.substringMax(UUID.randomUUID() + "_" + model.getUsername(), 200));
        model.setEnabled(false);
        repositoryUser.saveAndFlush(model);
    }

    @Transactional
    private void doSave(User model) {
        repositoryUser.saveAndFlush(model);
    }

    private User businessValidation(UserDto modelNew, ResponseMessage response) {
        if (modelNew.getId() == null) {

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

        return model;
    }

    private boolean existUsernameWithNotId(String username, Long userId) {
        return !repositoryUser.anyUsernameWithNotId(username, userId).equals(0l);
    }

    private boolean existUsername(String username) {
        return !repositoryUser.countByUsername(username).equals(0l);
    }
}
