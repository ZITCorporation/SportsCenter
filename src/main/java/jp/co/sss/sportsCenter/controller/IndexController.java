
package jp.co.sss.sportsCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("sample", "テスト画面だよ");
        return "index";
    }

    @RequestMapping("/listFacilities")
    public String listFacilities() {
        return "/facility/listFacilities";
    }
    
    @RequestMapping("/pool")
    public String pool() {
        return "/facility/pool";
    }
    
    @RequestMapping("/tennis")
    public String tennis() {
        return "/facility/tennis";
    }
}
