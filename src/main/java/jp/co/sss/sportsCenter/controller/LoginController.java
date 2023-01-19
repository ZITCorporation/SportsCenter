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
        return "/user/login";
    }

    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("form") @Valid LoginForm form, Model model) {
        User user = repository.findByEmailAndPassword(form.getEmail(), form.getPassword());
        if (user != null) {
            session.setAttribute("id", user.getUserId());
            session.setAttribute("name", user.getName());
            session.setAttribute("authority", user.getAuthority());
            session.setAttribute("login", "1");
            return "redirect:/";
        } else {
            model.addAttribute("errormessage", "メールアドレス、またはパスワードが間違っています。");
            // System.out.println("failed!");
            // System.out.println(form.getEmail() + " " + form.getPassword());
            return "/user/login";
        }
    }

    // セッション破棄
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}