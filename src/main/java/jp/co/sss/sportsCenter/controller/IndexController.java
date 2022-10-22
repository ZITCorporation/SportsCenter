
package jp.co.sss.sportsCenter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.sportsCenter.entity.User;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            User user = ((User) session.getAttribute("user"));
            model.addAttribute("sample", "Welcome Back, " + user.getName());
        } else {
            model.addAttribute("sample", "テスト画面だよ");
        }
        return "index";
    }

    @RequestMapping(value="/error", method=RequestMethod.GET)
    public String errorPage() {
        return "/error";
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
