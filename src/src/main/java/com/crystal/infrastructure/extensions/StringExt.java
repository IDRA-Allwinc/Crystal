package com.crystal.infrastructure.extensions;

import java.util.ArrayList;
import java.util.List;

public class StringExt {
    public static boolean isNullOrWhiteSpace(java.lang.String param) {
        return param == null || param.trim().length() == 0;
    }

    public static String naOnEmpty(String supUserDone) {

        if(isNullOrWhiteSpace(supUserDone))
            return "NA";

        return supUserDone;
    }

    public static String joinToString(List<Long> lstData) {
        if(lstData == null || lstData.size() == 0){
            return null;
        }

        String sRet = "";
        for (Long data : lstData){
            sRet = sRet + (sRet.isEmpty() ? "" : ", ") + data;
        }
        return sRet;
    }

    public static List<Long> getListIds(String sIds) {

        if(sIds == null)
            return null;

        String[] strArray = sIds.split(",");

        List<Long> lstIds = new ArrayList<>(strArray.length);

        for(int i = 0; i < strArray.length; i++) {
            try{
                String sVal = strArray[i].trim();
                lstIds.add(Long.parseLong(sVal));
            }catch (Exception e){
                continue;
            }
        }

        return lstIds;
    }

    public static String substringMax(String value, int max){
        if(value == null  || value.isEmpty())
            return value;

        int len = value.length();
        max = len < max ? len : max;
        return value.substring(0, max);
    }

}
