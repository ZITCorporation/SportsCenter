package jp.co.sss.sportsCenter.controller.LoginController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.ui.Model;

@Controller
public class LoginController {
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public String loginPost(Integer id, String password, Model model) {
        model.addAttribute("sample", "Login Success!");
        model.addAttribute("id", "User " + id + " welcome!");
        return "index";
    }
}