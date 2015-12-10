package com.crystal.service.shared;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class MainPageServiceImpl implements MainPageService {

//    @Autowired
//    SharedUserService sharedUserService;

//    @Autowired
//    UserRepository userRepository;

//    @Autowired
//    SystemSettingService systemSettingService;

    @Override
    public ModelAndView generatePage(String sRole, ModelAndView model, Long userId) {

        //systemSettingService.initSystemSettings();

        switch (sRole) {
            case "ROLE":
                //constructSupervisorMainPage(model, userId);
                return model;

            default:
                return model;
        }
    }

    /*
    private void constructSupervisorManagerMainPage(ModelAndView model, Long userId) {
        Gson json = new Gson();
        List<CommentMonitoringPlanNotice> lstGen = logCommentRepository.getEnabledCommentsByManagerSupRole(
                new ArrayList<String>() {{
                    add(MonitoringConstants.LOG_PENDING_ACCOMPLISHMENT);
                    add(MonitoringConstants.STATUS_PENDING_END);
                    add(MonitoringConstants.STATUS_PENDING_AUTHORIZATION);
                    add(MonitoringConstants.STATUS_PENDING_END);
                    add(MonitoringConstants.STATUS_END);
                    add(Constants.ACTION_AUTHORIZE_LOG_COMMENT);
                    add(MonitoringConstants.TYPE_INFORMATION);
                }}, userId
        );
        String sLstGeneric = json.toJson(lstGen);
        model.addObject("lstNotification", sLstGeneric);
        model.addObject("urlToGo", "/supervisorManager/log/deleteComment.json");
    }*/
}
