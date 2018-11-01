package com.apap.tutorial7.controller;

import com.apap.tutorial7.rest.Setting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @GetMapping(value = "/{kota}")
    public Object getAirport(@PathVariable("kota") String kota) throws Exception{

        String path = Setting.airportUrl + "&term=" + kota + "&country=ID&all_airports=true";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(path, Object.class);
    }
}
