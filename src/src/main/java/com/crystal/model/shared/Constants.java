package com.crystal.model.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    //codigos de tipos de entes fiscalizados (solo existen 2, subsecreatarias y organos independientes)
    public static final String ENTITY_TYPE_UNDERSECRETARY = "TYPE_UNDERSECRETARY";
    public static final String ENTITY_TYPE_INDEPENDENT_BODY = "TYPE_INDEPENDENT_BODY";

    public static final String appContext = "CRYSTAL";

    //nombre de usuario que otorga spring security cuando no se ha iniciado sesion
    public static final String anonymousUser = "anonymousUser";

    public static final String sessionCheckoutUrl = "/" + appContext + "/session/checkout.json";
    public static final String sessionExtendUrl = "/" + appContext + "/session/extend.json";

    public static final List<String> excludedUrls = new ArrayList<String>() {{
        add(sessionCheckoutUrl);
        add(sessionExtendUrl);
    }};

    public static final String FILE_PREFIX_USER = "USR_";
    public static final HashMap<String, Long> accessMap = new HashMap<>();

    /////////////////////////Settings/////////////////////////////////////
    public static class SystemSettings{
        public static final HashMap<String, String> Map = new HashMap<>();
        public static final String TOTAL_SESSION_LIMIT_TIME_KEY = "TOTAL_SESSION_LIMIT_TIME";
        public static final String LIMIT_TIME_KEY = "LIMIT_TIME";
        public static final String PATH_TO_SAVE_UPLOAD_FILES = "PATH_TO_SAVE_UPLOAD_FILES";
    }
    /////////////////////////Settings/////////////////////////////////////


}
