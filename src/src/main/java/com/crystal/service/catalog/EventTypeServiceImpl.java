package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.EventType;
import com.crystal.model.entities.catalog.dto.EventTypeDto;
import com.crystal.repository.catalog.EventTypeRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    EventTypeRepository repository;


    @Override
    public void upsert(Long id, ModelAndView modelView) {
        if (id != null) {
            Gson gson = new Gson();
            EventTypeDto model = repository.findOneDto(id);
            String sJson = gson.toJson(model);
            modelView.addObject("model", sJson);
        }
    }

    @Override
    public void save(EventTypeDto modelNew, ResponseMessage response) {

        EventType model = businessValidation(modelNew, response);

        if(response.isHasError())
            return;

        model.merge(modelNew);
        doSave(model);
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        EventType model = repository.findByIdAndIsObsolete(id, false);

        if(model == null){
            response.setHasError(true);
            response.setMessage("El tipo de evento ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar tipo de evento");
            return;
        }

        model.setObsolete(true);
        repository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(EventType model) {
        repository.saveAndFlush(model);
    }

    private EventType businessValidation(EventTypeDto modelNew, ResponseMessage response) {
        EventType model;
        if(modelNew.getId() == null){
            model = new EventType();
        }else{
            model = repository.findByIdAndIsObsolete(modelNew.getId(), false);
        }

        if(model == null){
            response.setHasError(true);
            response.setMessage("El tipo de evento no existe o ya fue eliminado.");
        }

        return model;
    }
}
