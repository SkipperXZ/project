package com.project.droneapi.controller;

import com.project.droneapi.model.AerialPhotograph;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AerialPhotographController {

    //test data
    private ArrayList<AerialPhotograph> aerialPhotographs = new ArrayList<>(Arrays.asList(
            new AerialPhotograph("image1","user1",123,124, LocalDateTime.now()),
            new AerialPhotograph("image2","user2",444,358, LocalDateTime.now()),
            new AerialPhotograph("image3","user1",743,895, LocalDateTime.now())

    ));

    public ArrayList<AerialPhotograph> getAerialPhotographs() {
        return aerialPhotographs;
    }

    @RequestMapping("/getAllAP")
    public ArrayList getAllAP(){
        return getAerialPhotographs();
    }

    @RequestMapping(value = "/getAPFromUser/{userID}",method= RequestMethod.GET)
    public ArrayList getAPFromUser(@PathVariable String userID){
        ArrayList<AerialPhotograph> apList = new ArrayList<>();
        for (AerialPhotograph ap:aerialPhotographs) {
            if(ap.getUserID().equalsIgnoreCase(userID)){
                apList.add(ap);
            }
        }
        return apList;
    }
}
