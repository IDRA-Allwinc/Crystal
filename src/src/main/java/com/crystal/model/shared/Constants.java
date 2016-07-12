package com.crystal.model.shared;

import java.io.File;
import java.util.HashMap;

public class Constants {

    //codigos para perfiles de usuario
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_DGPOP = "ROLE_DGPOP";
    public static final String ROLE_LINK = "ROLE_LINK";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    //authorities
    public static final String AUTHORITY_DGPOP = "DGPOP";
    public static final String AUTHORITY_LINK = "Enlace";
    public static final String AUTHORITY_MANAGER = "Administrador";
    public static final String AUTHORITY_DIRECTION = "Director";

    //codigos de tipos de entes fiscalizados (solo existen 2, subsecretarías y organos independientes)
    public static final String ENTITY_TYPE_SECRETARY = "TYPE_SECRETARY";
    public static final String ENTITY_TYPE_INDEPENDENT_BODY = "TYPE_INDEPENDENT_BODY";

    //nombre de usuario que otorga spring security cuando no se ha iniciado sesión
    public static final String anonymousUser = "anonymousUser";

    //session check urls
    public static final String sessionCheckoutUrl = "/session/checkout.json";
    public static final String sessionExtendUrl = "/session/extend.json";

    public static final String FILE_PREFIX_USER = "USR_";
    public static final String FILE_PREFIX_EXTENSION = "EXT_";
    public static final String FILE_PREFIX_REQUEST = "RQ_";
    public static final String FILE_PREFIX_COMMENT = "CM_";
    public static final String FILE_PREFIX_RECOMMENDATION = "RC_";
    public static final String FILE_PREFIX_OBSERVATION = "OBS_";
    public static final String FILE_PREFIX_RESPONSABILITY = "RES_";
    public static final String FILE_PREFIX_NONE = "NA_";

    public static final HashMap<String, Long> accessMap = new HashMap<>();

    public static final String redFlag = "1";
    public static final String yelllowFlag = "2";

    /////////////////////////Settings/////////////////////////////////////
    public static class SystemSettings {
        public static final HashMap<String, String> Map = new HashMap<>();
        public static final String TOTAL_SESSION_LIMIT_TIME_KEY = "TOTAL_SESSION_LIMIT_TIME";
        public static final String LIMIT_TIME_KEY = "LIMIT_TIME";
        public static final String PATH_TO_SAVE_UPLOAD_FILES = "PATH_TO_SAVE_UPLOAD_FILES";
        public static final String EMAIL_NOTIFICATION_SENDER = "EMAIL_NOTIFICATION_SENDER";
        public static final String EMAIL_NOTIFICATION_TEMPLATE_BODY = "EMAIL_NOTIFICATION_TEMPLATE_BODY";
    }
    /////////////////////////Settings/////////////////////////////////////

    /////////////////////////UploadFileTypes/////////////////////////////////////
    public static class UploadFile {
        public static final int EXTENSION_REQUEST= 2000;
        public static final int EXTENSION_COMMENT= 2010;
        public static final int EXTENSION_RECOMMENDATION= 2020;
        public static final int EXTENSION_OBSERVATION= 2030;
        public static final int EXTENSION_RESPONSIBILITY= 2040;

        public static final int REQUEST = 3000;
        public static final int LETTER = 4000;
        public static final int COMMENT = 5000;
        public static final int RECOMMENDATION = 6000;
        public static final int OBSERVATION = 7000;
        public static final int RESPONSIBILITY = 8000;
        public static final int EVENT = 9000;
    }
    /////////////////////////UploadFileTypes/////////////////////////////////////

    /////////////////////////Replication-Detail/////////////////////////////////////
    public static final String RECOMMENDATION_R = "RECOMMENDATION";
    public static final String OBSERVATION_R = "OBSERVATION";
    public static final String RESPONSIBILITY_R = "RESPONSIBILITY";

    public static final String EVENT_TYPE_MEETING = "MEETING";

    public static final String DATE_FORMAT_STR_PLACE_HOLDER = "dd/mm/aaaa";
    public static final String DATE_FORMAT_STR = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT_STR = "dd/MM/yyyy HH:mm";

    public static final String TEMPORAL_PATH_FILES= File.separator + "temporalDownloadedFiles" + File.separator;

}
