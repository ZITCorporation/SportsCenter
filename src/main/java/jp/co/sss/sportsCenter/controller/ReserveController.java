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
    
    // アーチェリー
    @RequestMapping(value="/facilities/archeryExplanation", method=RequestMethod.GET)
    public String toArcheryExplanation() {
        return "facility/archeryExplanation";
    }
    @RequestMapping(value="/facilities/archery", method=RequestMethod.GET)
    public String toArchery() {
        return "facility/archery";
    }
    
    // ジム
    @RequestMapping(value="/facilities/zymExplanation", method=RequestMethod.GET)
    public String toGymExplanation() {
        return "facility/gymExplanation";
    }
    @RequestMapping(value="/facilities/gym", method=RequestMethod.GET)
    public String toGym() {
        return "facility/gym";
    }
    
    // テニス
    @RequestMapping(value="/facilities/tennisExplanation", method=RequestMethod.GET)
    public String toTennisExplanation() {
        return "facility/tennisExplanation";
    }
    @RequestMapping(value="/facilities/tennis", method=RequestMethod.GET)
    public String toTennis() {
        return "facility/tennis";
    }
    
    // バスケットボール
    @RequestMapping(value="/facilities/basketballExplanation", method=RequestMethod.GET)
    public String toBasketballExplanation() {
        return "facility/basketballExplanation";
    }
    @RequestMapping(value="/facilities/basketball", method=RequestMethod.GET)
    public String toBasketball() {
        return "facility/basketball";
    }
    
    // プール
    @RequestMapping(value="/facilities/poolExplanation", method=RequestMethod.GET)
    public String toPoolExplanation() {
        return "facility/poolExplanation";
    }
    @RequestMapping(value="/facilities/pool", method=RequestMethod.GET)
    public String toPool() {
        return "facility/pool";
    }
}