package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.MeetingType;
import com.crystal.model.entities.catalog.MeetingTypeDto;
import com.crystal.repository.catalog.MeetingTypeRepository;
import com.crystal.repository.catalog.MeetingTypeRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {

    @Autowired
    MeetingTypeRepository repository;

    @Override
    public void upsert(Long id, ModelAndView modelView) {
        if (id != null) {
            Gson gson = new Gson();
            MeetingTypeDto model = repository.findOneDto(id);
            String sJson = gson.toJson(model);
            modelView.addObject("model", sJson);
        }
    }

    @Override
    public void save(MeetingTypeDto modelNew, ResponseMessage response) {

        MeetingType model = businessValidation(modelNew, response);

        if(response.isHasError())
            return;

        model.merge(modelNew);
        doSave(model);
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        MeetingType model = repository.findByIdAndIsObsolete(id, false);

        if(model == null){
            response.setHasError(true);
            response.setMessage("El tipo de reunión ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar tipo de reunión");
            return;
        }

        model.setObsolete(true);
        repository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(MeetingType model) {
        repository.saveAndFlush(model);
    }

    private MeetingType businessValidation(MeetingTypeDto modelNew, ResponseMessage response) {
        MeetingType model;
        if(modelNew.getId() == null){
            model = new MeetingType();
        }else{
            model = repository.findByIdAndIsObsolete(modelNew.getId(), false);
        }

        if(model == null){
            response.setHasError(true);
            response.setMessage("El tipo de reunión no existe o ya fue eliminado.");
        }

        return model;
    }
}
