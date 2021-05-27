package com.ipiecoles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler implements RequestHandler<Object, GatewayResponse> {

    @Override
    public GatewayResponse handleRequest(Object o, Context context){
        EphemerideService ephemerideService = new EphemerideService();
        Ephemeride ephemeride = null;

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("Access-Control-Allow-Origin","https://pjvilloud.github.io"); //Permets d'autoriser qui peut nous appeler.

        try{
            ephemeride = ephemerideService.getResponse(LocalDate.now());
        } catch (Exception e){
            //Error
            return new GatewayResponse("{\"error\":\"Problème lors de la récupération de la fête du jour\")", headers, 500);
        }

        String body = new Gson().toJson(ephemeride);
        return new GatewayResponse(body,headers,200);
    }

    //@Override //In case we want to take object via the web request receiver.
    public GatewayResponse handleRequestWithParameter(GatewayRequest requestParam, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "https://pjvilloud.github.io");
        String body = new Gson().toJson("Test");
        String temp = requestParam.getBody();

        //Check
        JsonObject jsonBody = null;
        JsonArray jsonInfo = null;

        if(requestParam.getBody().equals("")){
            return new GatewayResponse("Error your body can't be empty, please fill it, the body should follow the regex : ^\\{[^}]*\\}$\n",null,422);
        }

        if(!Pattern.matches("^\\{\"name\":\\[\\[[^\\]]*]]\\}$",requestParam.getBody())){
            return new GatewayResponse("Error, your body doesn't respect the regex : ^\\{\"name\":\\[\\[[^\\]]*]]\\}$",null,422);
        }

        try{
            jsonBody = new JsonParser().parse(requestParam.getBody()).getAsJsonObject();
            System.out.println(jsonBody);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new GatewayResponse(e.getMessage(),null,422);
        }
        try{
            jsonInfo = jsonBody.get("name").getAsJsonArray();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new GatewayResponse("Error : you didn't give a json with the name key, please edit your request body",null,422);
        }
        if(jsonInfo != null){
            String username = jsonInfo.get(0).getAsJsonArray().get(0).toString().replaceAll("\"","");
            for(KnownedNames aName : KnownedNames.values()){
                if(aName.toString().equals(username)){
                    body = "Hello : " + username + " you are a developer of this app";
                    break;
                } else {
                    body = "Hello : " + username + " I didn't kwow you i'm sorry.";
                }

            }
            return new GatewayResponse(body,null,200);
        }
        //System.out.println(jsonInfo);
        return new GatewayResponse("An error occured sorry, to ask why send a message to théodore, josé or maxime, thanks you for you feedback",null,500);
    }

    enum KnownedNames{
        theodore,
        jose,
        maxime,
        Theodore,
        Jose,
        Maxime,
        Théodore,
        José,
        théodore,
        josé
    }
}

