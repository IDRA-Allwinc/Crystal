package com.crystal.service.catalog;

import com.crystal.infrastructure.util.FileReader;
import com.crystal.model.entities.account.Role;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.catalog.*;
import com.crystal.model.shared.SystemSetting;
import com.crystal.repository.account.RoleRepository;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.*;
import com.crystal.repository.shared.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("insertCatalogService")
public class InsertCatalogServiceImpl implements InsertCatalogService {

    private String PATH = "C:\\Projects\\IDRASoft\\Crystal\\db\\";
//    private String PATH = "C:\\Users\\Developer\\Desktop\\repoCRYSTAL\\Crystal\\db\\";
//    private String PATH = "C:\\Users\\Administrator\\IdeaProjects\\Crystal\\db\\";
//    private String PATH = "/Users/ArturoDeLaRosa/Documents/Projects/Crystal/db/";

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void role() {
        List<String[]> lstDta = FileReader.readFile(PATH + "role.txt", "\\|", 4);

        for (String[] data : lstDta) {

            String code = data[3];

            Role model = roleRepository.findByCode(code);

            if (model == null) {
                model = new Role();
                model.setId(Long.parseLong(data[0]));
            }

            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setCode(data[3]);
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

            User model = userRepository.findByUsername(data[1]);

            if (model == null) {
                model = new User();
                model.setId(Long.parseLong(data[0]));
            }

            model.setUsername(data[1]);
            model.setPassword(data[2]);
            model.setEmail(data[3]);
            model.setFullName(data[4]);
            model.setEnabled(Boolean.parseBoolean(data[5]));
            final Role role = roleRepository.findByCode(data[6]);
            model.setRole(role);

            final AuditedEntity auditedEntity = auditedEntityRepository.findOne(Long.valueOf(data[7]));
            if (auditedEntity != null)
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
            AuditedEntity model = auditedEntityRepository.findOne(Long.parseLong(data[0]));

            if (model == null) {
                model = new AuditedEntity();
                model.setId(Long.parseLong(data[0]));
            }

            model.setName(data[1]);
            model.setResponsible(data[2]);
            model.setPhone(data[3]);
            model.setEmail(data[4]);
            model.setObsolete(data[5].equals("1"));
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
        List<String[]> lstDta = FileReader.readFile(PATH + "audited_entity_type.txt", "\\|", 5);

        for (String[] data : lstDta) {

            AuditedEntityType model = auditedEntityTypeRepository.findByCode(data[3]);

            if (model == null) {
                model = new AuditedEntityType();
                model.setId(Long.parseLong(data[0]));
            }

            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setCode(data[3]);
            model.setObsolete(data[4].equals("1"));
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
            AuditType model = auditTypeRepository.findOne(Long.parseLong(data[0]));
            if(model == null) {
                model = new AuditType();
                model.setId(Long.parseLong(data[0]));
            }

            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(data[3].equals("1"));
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
            EventType model = eventTypeRepository.findOne(Long.parseLong(data[0]));

            if (model == null) {
                model = new EventType();
                model.setId(Long.parseLong(data[0]));
            }

            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(data[3].equals("1"));
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
            MeetingType model = meetingTypeRepository.findOne(Long.parseLong(data[0]));
            if (model == null) {
                model = new MeetingType();
                model.setId(Long.parseLong(data[0]));
            }
            model.setName(data[1]);
            model.setDescription(data[2]);
            model.setObsolete(data[3].equals("1"));
            meetingTypeRepository.save(model);
        }

        meetingTypeRepository.flush();
    }

    @Autowired
    SupervisoryEntityRepository supervisoryEntityRepository;

    @Override
    public void supervisoryEntity() {
        List<String[]> lstDta = FileReader.readFile(PATH + "supervisory_entity.txt", "\\|", 7);

        for (String[] data : lstDta) {
            SupervisoryEntity model = supervisoryEntityRepository.findOne(Long.parseLong(data[0]));
            if (model == null) {
                model = new SupervisoryEntity();
                model.setId(Long.parseLong(data[0]));
            }

            model.setName(data[1]);
            model.setBelongsTo(data[2]);
            model.setResponsible(data[3]);
            model.setPhone(data[4]);
            model.setEmail(data[5]);
            model.setObsolete(data[6].equals("1"));
            supervisoryEntityRepository.save(model);
        }

        supervisoryEntityRepository.flush();
    }

    @Autowired
    CatFileTypeRepository catFileTypeRepository;

    @Override
    public void fileType() {
        List<String[]> lstDta = FileReader.readFile(PATH + "file_type.txt", "\\|", 5);
        for (String[] data : lstDta) {
            CatFileType model = catFileTypeRepository.findOne(Long.parseLong(data[0]));
            if (model == null) {
                model = new CatFileType();
                model.setId(Long.parseLong(data[0]));
            }

            model.setFileType(data[1]);
            model.setDescription(data[2]);
            model.setCode(data[3]);
            model.setObsolete(data[4].equals("1"));
            catFileTypeRepository.save(model);
        }
        catFileTypeRepository.flush();
    }

    @Autowired
    ObservationTypeRepository observationTypeRepository;

    @Override
    public void observationType() {
        List<String[]> lstDta = FileReader.readFile(PATH + "observation_type.txt", "\\|", 4);

        for (String[] data : lstDta) {
            ObservationType model = observationTypeRepository.findByCode(data[2]);
            if (model == null) {
                model = new ObservationType();
                model.setId(Long.parseLong(data[0]));
            }
            model.setName(data[1]);
            model.setCode(data[2]);
            model.setObsolete(data[3].equals("1"));
            observationTypeRepository.save(model);
        }
        observationTypeRepository.flush();

    }

    @Autowired
    SystemSettingRepository systemSettingRepository;

    @Override
    public void systemSettings() {
        List<String[]> lstDta = FileReader.readFile(PATH + "system_settings.txt", "\\|", 4);
        for (String[] data : lstDta) {
            SystemSetting model = systemSettingRepository.findByKey(data[1]);
            if (model == null) {
                model = new SystemSetting();
                model.setId(Long.parseLong(data[0]));
            }
            model.setKey(data[1]);
            model.setValue(data[2]);
            model.setDescription(data[3]);
            systemSettingRepository.save(model);
        }
        systemSettingRepository.flush();
    }

}