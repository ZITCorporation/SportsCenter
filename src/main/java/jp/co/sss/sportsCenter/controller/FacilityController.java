package jp.co.sss.sportsCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacilityController {// 施設一覧
    @RequestMapping(value = "/listFacilities", method = RequestMethod.GET)
    public String toFacility() {
        return "facility/listFacilities";
    }

    // アーチェリー
    @RequestMapping(value = "/archeryExplanation", method = RequestMethod.GET)
    public String toArcheryExplanation() {
        return "facility/explanation/archery";
    }

    // ジム
    @RequestMapping(value = "/gymExplanation", method = RequestMethod.GET)
    public String toGymExplanation() {
        return "facility/explanation/gym";
    }

    // テニス
    @RequestMapping(value = "/tennisExplanation", method = RequestMethod.GET)
    public String toTennisExplanation() {
        return "facility/explanation/tennis";
    }

    // バスケットボール
    @RequestMapping(value = "/basketballExplanation", method = RequestMethod.GET)
    public String toBasketballExplanation() {
        return "facility/explanation/basketball";
    }

    // プール
    @RequestMapping(value = "/poolExplanation", method = RequestMethod.GET)
    public String toPoolExplanation() {
        return "facility/explanation/pool";
    }

}
