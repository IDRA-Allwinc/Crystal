package com.crystal.service.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.dto.AuditDto;
import com.crystal.model.entities.audit.view.AuditView;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.catalog.*;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    SupervisoryEntityRepository supervisoryEntityRepository;
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    AuditedEntityRepository auditedEntityRepository;
    @Autowired
    AuditRepository auditRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuditTypeRepository auditTypeRepository;

    @Autowired
    SharedUserService sharedUserService;

    @Override
    public void upsert(Long id, ModelAndView modelView) {

        Gson gson = new Gson();

        AuditDto model;
        if (id != null) {
            model = auditRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaRepository.findSelectedAreasByAuditId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));

        } else {
            model = new AuditDto();
        }

        model.setRole(roleRepository.findOne(sharedUserService.getRoleIdForUser()).getCode());
        List<SelectList> lstAuditTypes = auditTypeRepository.findNoObsolete();
        modelView.addObject("lstAuditTypes", lstAuditTypes);

        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public List<SelectList> getSupervisoryEntities(String supervisoryStr) {
        return supervisoryEntityRepository.findSupervisoryEntitiesByStr("%" + supervisoryStr + "%");
    }

    @Override
    public Object getAuditsByRole(GridService gridService) {

        HashMap<String, Object> filters = new HashMap<>();

        if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_DGPOP)) {
            filters.put("auditedEntityTypeCode", Constants.ENTITY_TYPE_UNDERSECRETARY);
            return gridService.toGrid(AuditView.class, filters);
        } else if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_LINK)) {
            filters.put("auditedEntityId", sharedUserService.getAuditedEntityIdByLoggedUserId(sharedUserService.getLoggedUserId()));
            return gridService.toGrid(AuditView.class, filters);
        }
        return null;
    }

    @Override
    public void fillAudit(Long id, ModelAndView modelView) {
        Gson gson = new Gson();

        AuditDto model;
        model = auditRepository.findDtoById(id);
        List<SelectList> lstSelectedAreas = areaRepository.findSelectedAreasByAuditId(id);
        modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        model.setRole(roleRepository.findOne(sharedUserService.getRoleIdForUser()).getCode());
        List<SelectList> lstAuditTypes = auditTypeRepository.findNoObsolete();
        modelView.addObject("lstAuditTypes", lstAuditTypes);
        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void doObsolete(Long id, Long userId, ResponseMessage response) {
        Audit model = auditRepository.findByIdAndIsObsolete(id, false);

        if(model == null){
            response.setHasError(true);
            response.setMessage("La auditoría ya fue eliminada o no existe en el sistema");
            response.setTitle("Eliminar auditoría");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(userId);
        auditRepository.saveAndFlush(model);
    }
}
