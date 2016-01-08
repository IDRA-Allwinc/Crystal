package com.crystal.component;

import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SystemSetting;
import com.crystal.repository.shared.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitializerEventListener {

    @Autowired
    SystemSettingRepository systemSettingRepository;

    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshEventInitDone()
    {
        if (Constants.SystemSettings.Map.size() == 0) {
            System.out.println("Obteniendo valores de configuración del sistema");
            List<SystemSetting> lstSettings = systemSettingRepository.findAll();
            for (SystemSetting setting : lstSettings) {
                Constants.SystemSettings.Map.put(setting.getKey(), setting.getValue());
            }
        }
    }

}
