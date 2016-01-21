package com.crystal.service.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.dto.AuditDto;
import com.crystal.model.entities.audit.view.AuditView;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.catalog.*;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
            if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_LINK)) {
                SelectList sl = new SelectList();
                AuditedEntity ae = auditedEntityRepository.findOne(sharedUserService.getAuditedEntityIdByLoggedUserId(sharedUserService.getLoggedUserId()));
                sl.setId(ae.getId());
                sl.setName(ae.getName());
                sl.setDescription(ae.getResponsible());
                model.setAuditedEntity(sl);
            }
        }

        model.setRole(roleRepository.findOne(sharedUserService.getRoleIdForUser()).getCode());
        List<SelectList> lstAuditTypes = auditTypeRepository.findNoObsolete();
        modelView.addObject("lstAuditTypes", new Gson().toJson(lstAuditTypes));

        modelView.addObject("model", gson.toJson(model));
    }

    private Audit businessValidation(AuditDto auditDto, ResponseMessage responseMessage) {
        Audit audit;

        if (auditDto.getId() != null) {
            audit = auditRepository.findByIdAndIsObsolete(auditDto.getId(), false);

            if (audit == null) {
                responseMessage.setHasError(true);
                responseMessage.setMessage("La auditor&iacute;a no existe en el sistema o ya fue eliminada.");
                return null;
            }

            if (auditRepository.findByLetterNumberAndId(auditDto.getLetterNumber(), auditDto.getId()) != null) {
                responseMessage.setHasError(true);
                responseMessage.setMessage("Ya existe una auditor&iacute;a con el mismo n&uacute;mero de oficio. Por favor revise la informaci&oacute;n e intente de nuevo.");
                return null;
            }

            audit.setUpdAudit(sharedUserService.getLoggedUserId());

        } else {
            audit = new Audit();
            audit.setInsAudit(sharedUserService.getLoggedUserId());
        }

        audit.merge(auditDto);

        return audit;
    }

    @Override
    public void save(AuditDto auditDto, ResponseMessage responseMessage) {

        Audit audit;
        audit = businessValidation(auditDto, responseMessage);

        if (audit == null) {
            return;
        }

        audit.setSupervisoryEntity(supervisoryEntityRepository.findOne(auditDto.getSupervisoryEntityId()));

        if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_LINK))
            audit.setAuditedEntity(auditedEntityRepository.findOne(sharedUserService.getAuditedEntityIdByLoggedUserId(sharedUserService.getLoggedUserId())));
        else
            audit.setAuditedEntity(auditedEntityRepository.findOne(auditDto.getAuditedEntityId()));

        audit.setAuditType(auditTypeRepository.findOne(auditDto.getAuditTypeId()));

        List<SelectList> lstSelectedAreas = new Gson().fromJson(auditDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
        }.getType());

        if (audit.getLstAreas() != null) {
            audit.setLstAreas(null);
        }

        List<Area> lstNewSelectedAreas = null;
        for (SelectList item : lstSelectedAreas) {
            lstNewSelectedAreas = new ArrayList<>();
            Area a = new Area();
            a.setId(item.getId());
            lstNewSelectedAreas.add(a);
        }

        audit.setLstAreas(lstNewSelectedAreas);

        doSave(audit);
    }

    @Override
    @Transactional
    public void doSave(Audit audit) {
        auditRepository.saveAndFlush(audit);
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
//        Gson gson = new Gson();
//
//        AuditDto model;
//        model = auditRepository.findDtoById(id);
//        List<SelectList> lstSelectedAreas = areaRepository.findSelectedAreasByAuditId(id);
//        modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
//        model.setRole(roleRepository.findOne(sharedUserService.getRoleIdForUser()).getCode());
//        List<SelectList> lstAuditTypes = auditTypeRepository.findNoObsolete();
//        modelView.addObject("lstAuditTypes", lstAuditTypes);
//        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void doObsolete(Long id, Long userId, ResponseMessage response) {
        Audit model = auditRepository.findByIdAndIsObsolete(id, false);

        if (model == null) {
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