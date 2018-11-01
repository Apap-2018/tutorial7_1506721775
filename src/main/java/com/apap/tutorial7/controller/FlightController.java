package com.apap.tutorial7.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;

    @PostMapping(value = "/add")
    private FlightModel addFlightSubmit(@RequestBody FlightModel flightModel){
        return flightService.addFlight(flightModel);
    }

    @PutMapping(value = "/update/{flightId}")
    public String updateFlightSubmit(@PathVariable("flightId") Long flightId,
                                    @RequestParam(value = "destination", required = false) String destination,
                                    @RequestParam(value = "origin", required = false) String origin,
                                     @RequestParam(value = "time", required = false) String time) throws ParseException {

        FlightModel flight = flightService.findById(flightId);

        if (flight.equals(null)){
            return "Couldn't find your flight";
        }

        if (!destination.equals("")){
            flight.setDestination(destination);
        }

        if (!time.equals("")){
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(time);
            java.sql.Date timeNew = new java.sql.Date(date.getTime());
            flight.setTime(timeNew);
        }

        if (!origin.equals("")){
            flight.setOrigin(origin);
        }

        flightService.addFlight(flight);

        return "Flight Update Success";
    }

    @GetMapping(value = "/view/{flightNumber}")
    private FlightModel viewFlight(@PathVariable("flightNumber") String flightNumber) {
       return flightService.getFlightDetailByFlightNumber(flightNumber).get();
    }

    @GetMapping(value = "/all" )
    private List<FlightModel> viewAllFlight() {
        return flightService.findAll();
    }

    @DeleteMapping(value = "/{flightId}")
    private String deleteFlightSubmit(@PathVariable("flightId") Long flightId){
        flightService.deleteById(flightId);
        return "Flight has been deleted";
    }

//    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
//    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
//        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
//        pilot.setListFlight(new ArrayList<FlightModel>(){
//            private ArrayList<FlightModel> init(){
//                this.add(new FlightModel());
//                return this;
//            }
//        }.init());
//
//        model.addAttribute("pilot", pilot);
//        return "add-flight";
//    }
//
//    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"addRow"})
//    private String addRow(@ModelAttribute PilotModel pilot, Model model) {
//        pilot.getListFlight().add(new FlightModel());
//        model.addAttribute("pilot", pilot);
//        return "add-flight";
//    }
//
//    @RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
//    public String removeRow(@ModelAttribute PilotModel pilot, Model model, HttpServletRequest req) {
//        Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
//        pilot.getListFlight().remove(rowId.intValue());
//
//        model.addAttribute("pilot", pilot);
//        return "add-flight";
//    }
//
//    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
//    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
//        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).get();
//        for (FlightModel flight : pilot.getListFlight()) {
//            if (flight != null) {
//                flight.setPilot(archive);
//                flightService.addFlight(flight);
//            }
//        }
//        return "add";
//    }
//
//
//    @RequestMapping(value = "/flight/view", method = RequestMethod.GET)
//    private @ResponseBody FlightModel view(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
//        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
//        return archive;
//    }
//
//    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
//    private String delete(@ModelAttribute PilotModel pilot, Model model) {
//        for (FlightModel flight : pilot.getListFlight()) {
//            flightService.deleteByFlightNumber(flight.getFlightNumber());
//        }
//        return "delete";
//    }
//
//    @RequestMapping(value = "/flight/update", method = RequestMethod.GET)
//    private String update(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
//        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
//        model.addAttribute("flight", archive);
//        return "update-flight";
//    }
//
//    @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
//    private @ResponseBody FlightModel updateFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
//        flightService.addFlight(flight);
//        return flight;
//    }
}