package com.crystal.service.catalog;

import com.crystal.infrastructure.FileReader;
import com.crystal.model.entities.account.Role;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.catalog.*;
import com.crystal.repositroy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("insertCatalogService")
public class InsertCatalogServiceImpl implements InsertCatalogService {

    private String PATH = "C:\\Users\\Israel\\Desktop\\repoUmecaWeb\\UmecaApp\\db\\";

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void role() {
        List<String[]> lstDta = FileReader.readFile(PATH + "role.txt", "\\|", 3);

        for (String[] data : lstDta) {
            Role model = new Role();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setDescription(data[2]);
            roleRepository.save(model);
        }

        roleRepository.flush();
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void user() {
        List<String[]> lstDta = FileReader.readFile(PATH + "user.txt", "\\|", 8);

        for (String[] data : lstDta) {
            User model = new User();
            model.setId(Long.parseLong(data[0]));
            model.setUsername(data[1]);
            model.setPassword(data[2]);
            model.setEmail(data[3]);
            model.setFullname(data[4]);
            model.setEnabled(Boolean.parseBoolean(data[5]));
            final Role role = roleRepository.findByCode(data[6]);
            model.setRole(role);

            final AuditedEntity auditedEntity = auditedEntityRepository.findOne(Long.valueOf(data[7]));
            if(auditedEntity!=null)
                model.setAuditedEntity(auditedEntity);

            userRepository.save(model);
        }

        userRepository.flush();
    }

    @Autowired
    AuditedEntityRepository auditedEntityRepository;

    @Override
    public void auditedEntity() {
        List<String[]> lstDta = FileReader.readFile(PATH + "audited_entity.txt", "\\|", 7);

        for (String[] data : lstDta) {
            AuditedEntity model = new AuditedEntity();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setResponsible(data[2]);
            model.setPhone(data[3]);
            model.setEmail(data[4]);
            model.setObsolete(Boolean.parseBoolean(data[5]));
            final AuditedEntityType entityType = auditedEntityTypeRepository.findByCode(data[6]);
            model.setAuditedEntityType(entityType);
            auditedEntityRepository.save(model);
        }

        auditedEntityRepository.flush();
    }

    @Autowired
    AuditedEntityTypeRepository auditedEntityTypeRepository;

    @Override
    public void auditedEntityType() {
        List<String[]> lstDta = FileReader.readFile(PATH + "audited_entity_type.txt", "\\|", 4);

        for (String[] data : lstDta) {
            AuditedEntityType model = new AuditedEntityType();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(Boolean.parseBoolean(data[3]));
            auditedEntityTypeRepository.save(model);
        }

        auditedEntityTypeRepository.flush();
    }


    @Autowired
    AuditTypeRepository auditTypeRepository;

    @Override
    public void auditType() {
        List<String[]> lstDta = FileReader.readFile(PATH + "audit_type.txt", "\\|", 4);

        for (String[] data : lstDta) {
            AuditType model = new AuditType();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(Boolean.parseBoolean(data[3]));
            auditTypeRepository.save(model);
        }

        auditTypeRepository.flush();
    }

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Override
    public void eventType() {
        List<String[]> lstDta = FileReader.readFile(PATH + "event_type.txt", "\\|", 4);

        for (String[] data : lstDta) {
            EventType model = new EventType();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(Boolean.parseBoolean(data[3]));
            eventTypeRepository.save(model);
        }

        eventTypeRepository.flush();
    }

    @Autowired
    MeetingTypeRepository meetingTypeRepository;

    @Override
    public void meetingType() {
        List<String[]> lstDta = FileReader.readFile(PATH + "meeting_type.txt", "\\|", 4);

        for (String[] data : lstDta) {
            MeetingType model = new MeetingType();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(Boolean.parseBoolean(data[3]));
            meetingTypeRepository.save(model);
        }

        meetingTypeRepository.flush();
    }

    @Autowired
    SupervisoryEntityRepository supervisoryEntityRepository;

    @Override
    public void supervisoryEntity() {
        List<String[]> lstDta = FileReader.readFile(PATH + "supervisory_type.txt", "\\|", 7);

        for (String[] data : lstDta) {
            SupervisoryEntity model = new SupervisoryEntity();
            model.setId(Long.parseLong(data[0]));
            model.setName(data[1]);
            model.setBelongsTo(data[2]);
            model.setResponsible(data[3]);
            model.setPhone(data[4]);
            model.setEmail(data[5]);
            model.setObsolete(Boolean.parseBoolean(data[6]));
            supervisoryEntityRepository.save(model);
        }

        supervisoryEntityRepository.flush();
    }

}