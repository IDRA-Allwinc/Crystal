package com.crystal.service.account;

import com.crystal.model.entities.account.UserDto;
import com.crystal.model.shared.Constants;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditedEntityRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserServiceImpl implements UserService {

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
            sJson = gson.toJson(model);
            modelView.addObject("model", sJson);
        }
    }
}
