package com.example.parks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.parks.controller.AppController;
import com.example.parks.model.Activities;
import com.example.parks.model.EntranceFees;
import com.example.parks.model.Images;
import com.example.parks.model.OperatingHours;
import com.example.parks.model.Park;
import com.example.parks.model.StandardHours;
import com.example.parks.model.Topics;
import com.example.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getParks(final AsyncResponse callback,String code){
        String url = Util.getParksUrl(code);
        JsonObjectRequest jsonObjectRequest
                = new JsonObjectRequest(Request.Method.GET, url,null,response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i <jsonArray.length() ; i++) {
                    Park park = new Park();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setStates(jsonObject.getString("states"));
                    JSONArray imageList = jsonObject.getJSONArray("images");
                    List<Images> list = new ArrayList<>();
                    for (int j = 0; j <imageList.length() ; j++) {
                        Images images = new Images();
                        images.setCredit(imageList.getJSONObject(j).getString("credit"));
                        images.setTitle(imageList.getJSONObject(j).getString("title"));
                        images.setUrl(imageList.getJSONObject(j).getString("url"));
                        list.add(images);
                    }
                    park.setImages(list);
                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setName(jsonObject.getString("name"));
                    park.setDesignation(jsonObject.getString("designation"));
                    park.setDescription(jsonObject.getString("description"));

                    JSONArray activityJSONArray = jsonObject.getJSONArray("activities");
                    List<Activities> activityList = new ArrayList<>();
                    for (int j = 0; j < activityJSONArray.length(); j++) {
                        Activities activities = new Activities();
                        activities.setId(activityJSONArray.getJSONObject(j).getString("id"));
                        activities.setName(activityJSONArray.getJSONObject(j).getString("name"));
                        activityList.add(activities);
                    }
                    park.setActivities(activityList);

                    JSONArray topicsJSONArray = jsonObject.getJSONArray("topics");
                    List<Topics> topicList = new ArrayList<>();
                    for (int j = 0; j < topicsJSONArray.length(); j++) {
                        Topics topics = new Topics();
                        topics.setId(topicsJSONArray.getJSONObject(j).getString("id"));
                        topics.setName(topicsJSONArray.getJSONObject(j).getString("name"));
                        topicList.add(topics);
                    }
                    park.setTopics(topicList);

                    JSONArray operatingHoursJSONArray = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> operatingHoursList = new ArrayList<>();
                    for (int j = 0; j <operatingHoursJSONArray.length() ; j++) {
                        OperatingHours operatingHours = new OperatingHours();
                        operatingHours.setDescription(operatingHoursJSONArray.getJSONObject(j).getString("description"));
                        StandardHours standardHours = new StandardHours();
                        JSONObject hours = operatingHoursJSONArray.getJSONObject(j).getJSONObject("standardHours");
                        standardHours.setWednesday(hours.getString("wednesday"));
                        standardHours.setMonday(hours.getString("monday"));
                        standardHours.setThursday(hours.getString("thursday"));
                        standardHours.setSunday(hours.getString("sunday"));
                        standardHours.setTuesday(hours.getString("tuesday"));
                        standardHours.setFriday(hours.getString("friday"));
                        standardHours.setSaturday(hours.getString("saturday"));
                        operatingHours.setStandardHours(standardHours);
                        operatingHoursList.add(operatingHours);
                    }
                    park.setOperatingHours(operatingHoursList);
                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                    JSONArray entranceFeesJSONArray = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> entranceFeesList = new ArrayList<>();
                    for (int j = 0; j < entranceFeesJSONArray.length(); j++) {
                        EntranceFees entranceFees = new EntranceFees();
                        entranceFees.setCost(entranceFeesJSONArray.getJSONObject(j).getString("cost"));
                        entranceFees.setDescription(entranceFeesJSONArray.getJSONObject(j).getString("description"));
                        entranceFees.setTitle(entranceFeesJSONArray.getJSONObject(j).getString("title"));
                        entranceFeesList.add(entranceFees);
                    }
                    park.setEntranceFees(entranceFeesList);

                    parkList.add(park);
                }
                if (callback!=null){
                    callback.processPark(parkList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {
                    error.printStackTrace();
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
