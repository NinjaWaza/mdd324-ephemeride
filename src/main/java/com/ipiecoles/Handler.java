package com.ipiecoles;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
            return new GatewayResponse("{\"error\":\"Problème lors de la récupération de la citation du jour\")", headers, 500);
        }

        String body = new Gson().toJson(ephemeride);
        return new GatewayResponse(body,headers,200);
    }

    //@Override //In case we want to take object via the web request receiver.
    public GatewayResponse handleRequestWithParameter(GatewayRequest o, Context context) {
        //Horoscope horoscope = new Gson().fromJson(o.getBody(), Horoscope.class);
        //System.out.println("Horoscope : " + horoscope.getSigne());
        //CitationService citationService = new CitationService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "https://pjvilloud.github.io");
        //Quote quote = null;
        try {
            //quote = citationService.getQuoteOfTheDay();
        } catch (/*IOException*/Exception e) {
            //Gestion d'erreur
            return new GatewayResponse("{\"error\":\"Problème lors de la récupération de la citation du jour\")", headers, 500);
        }
        //String body = new Gson().toJson(quote);
        //return new GatewayResponse(body, headers, 200);
        return null;
    }

}
