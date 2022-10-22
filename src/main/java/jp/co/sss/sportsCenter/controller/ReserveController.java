package jp.co.sss.sportsCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ReserveController {
    @RequestMapping(value="/facilities", method=RequestMethod.GET)
    public String toFacility() {
        return "facility/listFacilities";
    }
    @RequestMapping(value="/facilities/pool", method=RequestMethod.GET)
    public String toPool() {
        return "facility/pool";
    }
    @RequestMapping(value="/facilities/tennis", method=RequestMethod.GET)
    public String toTennis() {
        return "facility/tennis";
    }
    
}