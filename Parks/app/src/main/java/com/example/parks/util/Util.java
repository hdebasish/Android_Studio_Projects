package com.example.parks.util;

public class Util {

    public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?stateCode=&api_key=gikOb18gvWnHY1QhXD87xLEK3gMybjx2jH0fcYqO";

    public static String getParksUrl(String code){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+code+"&api_key=gikOb18gvWnHY1QhXD87xLEK3gMybjx2jH0fcYqO";
    }
}
