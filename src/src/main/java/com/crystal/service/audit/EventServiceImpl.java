package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Assistant;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Event;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.EventDto;
import com.crystal.model.entities.catalog.EventType;
import com.crystal.model.entities.catalog.MeetingType;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.EventRepository;
import com.crystal.repository.catalog.EventTypeRepository;
import com.crystal.repository.catalog.MeetingTypeRepository;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    MeetingTypeRepository meetingTypeRepository;

    @Autowired
    UploadFileGenericRepository repositoryUf;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    SharedUserService sharedUserService;


    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();

        EventDto model;
        if (id != null) {
            model = eventRepository.findDtoById(id);
            List<SelectList> lstSelectedAssistants = eventRepository.findSelectedAssistantsByEventId(id);
            modelView.addObject("lstSelectedAssistants", gson.toJson(lstSelectedAssistants));
        } else {
            model = new EventDto();
            model.setAuditId(auditId);
        }
        modelView.addObject("lstEventType", gson.toJson(eventTypeRepository.findNoObsolete()));
        modelView.addObject("lstMeetingType", gson.toJson(meetingTypeRepository.findNoObsolete()));
        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void save(EventDto modelNew, ResponseMessage response) throws ParseException {

        Event model = businessValidation(modelNew, null, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    public void doObsolete(Long id, ResponseMessage response) {

        Event model = eventRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El evento ya fue eliminado o no existe en el sistema.");
            response.setTitle("Eliminar evento");
            return;
        }

//        if (model.getLstExtension() != null && model.getLstExtension().size() > 0) {
//            response.setHasError(true);
//            response.setMessage("No es posible eliminar una observaci&oacute;n que ya tiene una prorroga.");
//            response.setTitle("Eliminar observaci&oacute;n");
//            return;
//        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        eventRepository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(Event model) {
        eventRepository.saveAndFlush(model);
    }

    private Event businessValidation(EventDto eventDto, AttentionDto attentionDto, ResponseMessage responseMessage) throws ParseException {
        Event event = null;

        if (eventDto != null) {

            EventType evenType = eventTypeRepository.findOne(eventDto.getEventTypeId());
            Long id = eventDto.getId();

            if (id != null) {
                event = eventRepository.findByIdAndIsObsolete(id, false);
                if (event == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("El evento ya fue eliminado o no existe en el sistema.");
                    return null;
                }

                event.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                event = new Event();
                event.setCreateDate(Calendar.getInstance());
                event.setInsAudit(sharedUserService.getLoggedUserId());

                if (evenType.getCode() != null && evenType.getCode().equals(Constants.EVENT_TYPE_MEETING)) {
                    Calendar meeting = Calendar.getInstance();
                    meeting.setTime(sdf.parse(eventDto.getMeetingDate() + " " + eventDto.getMeetingHour()));
                    event.setMeetingDate(meeting);

                    MeetingType mt = new MeetingType();
                    mt.setId(eventDto.getMeetingTypeId());
                    event.setMeetingType(mt);

                    List<SelectList> lstSelectedAssistants = new Gson().fromJson(eventDto.getLstSelectedAssistants(), new TypeToken<List<SelectList>>() {
                    }.getType());

                    if (event.getLstAssistant() != null) {
                        event.setLstAssistant(null);
                    }

                    List<Assistant> lstNewSelectedAssistants;


                    if (lstSelectedAssistants != null && lstSelectedAssistants.size() > 0) {
                        lstNewSelectedAssistants = new ArrayList<>();
                        for (SelectList item : lstSelectedAssistants) {
                            Assistant a = new Assistant();
                            a.setName(item.getResponsible());
                            a.setBelongsTo(item.getName());
                            a.setEmail(item.getEmail());
                            a.setPhone(item.getPhone());
                            a.setEvent(event);
                            lstNewSelectedAssistants.add(a);
                        }
                        event.setLstAssistant(lstNewSelectedAssistants);
                    } else {
                        responseMessage.setHasError(true);
                        responseMessage.setMessage("Debe seleccionar al menos un &aacute;rea.");
                        return null;
                    }

                }
            }

            event.setDescription(eventDto.getDescription());
            event.setEventType(evenType);

            Audit a = auditRepository.findOne(eventDto.getAuditId());
            event.setAudit(a);
        }


        return event;
    }

    @Override
    public void upsertViewDocs(Long eventId, ModelAndView modelAndView) {
        EventDto model = eventRepository.findDtoById(eventId);
        model.setType(Constants.UploadFile.EVENT);
        model.setDescription("");
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long eventId, Long upFileId, ResponseMessage response) {
        Event model = eventRepository.findByIdAndIsObsolete(eventId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El evento ya fue eliminado o no existe en el sistema.");
            response.setTitle("Eliminar documento");
            return;
        }

        List<UploadFileGeneric> lstFiles = model.getLstFiles();

        for (int i = lstFiles.size() - 1; i >= 0; i--) {
            if (lstFiles.get(i).getId().equals(upFileId))
                lstFiles.remove(i);
        }

        eventRepository.saveAndFlush(model);
    }

}
