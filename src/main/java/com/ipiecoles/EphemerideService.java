package com.ipiecoles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.StringJoiner;
import java.time.format.DateTimeFormatter;


public class EphemerideService {
    private WebUtils webUtils = new WebUtils();

    /**
     * Méthode allant chercher toutes les informations d'une année
     * @return Json avec toutes les informations
     */
    public JsonObject getEphemerideAnnee(){
        String url = "https://raw.githubusercontent.com/theofidry/ephemeris/master/src/ephemeris.json";
        String contentOfTheUrl="";
        try{
            contentOfTheUrl = webUtils.getPageContents(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JsonObject jsonObject = new JsonParser().parse(contentOfTheUrl).getAsJsonObject();
        return jsonObject;
    }

    public JsonArray getEphemerideMonth(JsonObject jsonObject, String monthYouWant){
        JsonArray monthAsJson = jsonObject.get(monthYouWant.toLowerCase()).getAsJsonArray();
        return monthAsJson;
    }

    public String getEphemerideDay(JsonArray monthEphemeride, Integer dayYouWant){
        String ephemerideOfTheDay = "";
        JsonArray dayAsJson = monthEphemeride.get(dayYouWant).getAsJsonArray();
        if(!dayAsJson.get(1).equals("")){
            ephemerideOfTheDay = dayAsJson.get(1).toString() + " ";
        }
        ephemerideOfTheDay += dayAsJson.get(0).toString();
        return ephemerideOfTheDay.replaceAll("\"","");
    }

    public String getEphemeride(LocalDate date){
        JsonObject year = this.getEphemerideAnnee();
        JsonArray month = this.getEphemerideMonth(year, date.getMonth().toString());
        String ephemeride = this.getEphemerideDay(month, date.getDayOfMonth()-1);
        return ephemeride;
    }

    public Ephemeride getResponse(LocalDate date){
        LocalDate aujourdhui = date;
        Ephemeride ephemeride = new Ephemeride();
        ephemeride.setDateJour(aujourdhui.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")));
        ephemeride.setFeteDuJour(this.getEphemeride(aujourdhui));
        ephemeride.setJourAnnee(aujourdhui.getDayOfYear());
        ephemeride.setJoursRestants(aujourdhui.isLeapYear() ? 366 - aujourdhui.getDayOfYear() : 365 - aujourdhui.getDayOfYear());
        ephemeride.setNumSemaine((aujourdhui.getDayOfYear() / 7) + 1);
        return ephemeride;
    }
}
