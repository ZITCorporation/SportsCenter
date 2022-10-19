package jp.co.sss.sportsCenter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.sportsCenter.entity.User;
import jp.co.sss.sportsCenter.form.LoginForm;
import jp.co.sss.sportsCenter.repository.UserRepository;

@Controller
public class LoginController {
    @Autowired
    UserRepository repository;
    
    @Autowired
    HttpSession session;
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("form") @Valid LoginForm form, Model model) {
        User user = repository.findByIdAndPassword(form.getId(), form.getPassword());
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("loginStatus", 1);
            // model.addAttribute("logout", "/list");
        } else {
            model.addAttribute("errormessage", "ID、またはパスワードが間違っています。");
            System.out.println("failed!");
            return "login";
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}