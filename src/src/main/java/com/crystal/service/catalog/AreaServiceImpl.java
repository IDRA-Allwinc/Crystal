package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.dto.AreaDto;
import com.crystal.model.shared.Constants;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AreaRepository;
import com.crystal.repository.catalog.AuditedEntityRepository;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    AuditedEntityRepository auditedEntityRepository;
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    UserRepository userRepository;

    public void upsert(Long id, ModelAndView modelAndView) {

        AreaDto ae = null;
        Gson gson = new Gson();

        if (id != null) {
            ae = areaRepository.findDtoById(id);
            modelAndView.addObject("model", gson.toJson(ae));
        }

        if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_DGPOP))
            modelAndView.addObject("lstAuditedEntity", gson.toJson(auditedEntityRepository.findNoObsoleteByType(Constants.ENTITY_TYPE_UNDERSECRETARY)));
    }

    @Transactional
    public ResponseMessage doObsolete(Long id, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha eliminado la información correctamente.");
        Area ae = areaRepository.findOne(id);
        ae.setObsolete(true);
        areaRepository.save(ae);
        return responseMessage;
    }

    @Transactional
    public ResponseMessage save(AreaDto areaDto, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha guardado la información correctamente.");
        areaRepository.save(mergeArea(areaDto));
        return responseMessage;
    }

    private Area mergeArea(AreaDto areaDto) {

        Area ae = new Area();
        if (areaDto.getId() != null) {
            ae = areaRepository.findOne(areaDto.getId());
        }

        ae.setName(areaDto.getName());
        ae.setResponsible(areaDto.getResponsible());
        ae.setPhone(areaDto.getPhone());
        ae.setEmail(areaDto.getEmail());

        if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_DGPOP))
            ae.setAuditedEntity(auditedEntityRepository.findOne(areaDto.getAuditedEntityId()));
        else
            ae.setAuditedEntity(auditedEntityRepository.findByUserId(sharedUserService.GetLoggedUserId()));

        return ae;
    }
}


