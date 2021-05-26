package com.ipiecoles;

import java.io.IOException;
import java.time.LocalDate;
import com.google.gson.Gson;


public class Main {
    public static void main(String[] args) throws IOException {
        EphemerideService ephemerideService = new EphemerideService();
        //System.out.println(ephemerideService.getResponse());
        System.out.println(new Gson().toJson(ephemerideService.getResponse(LocalDate.now())));
        System.out.println(new Handler().handleRequest(null, null));
    }
}
