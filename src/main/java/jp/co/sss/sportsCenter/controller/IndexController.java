
package jp.co.sss.sportsCenter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.sportsCenter.entity.User;


@Controller
public class IndexController {
    
// cd sportsCenter
// mvn spring-boot:run
// http://localhost:7777/

    // ホーム
    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            User user = ((User) session.getAttribute("user"));
            model.addAttribute("sample", "Welcome Back, " + user.getName());
        } else {
            model.addAttribute("sample", "ログインしましょう！");
        }
        return "index";
    }

    // エラー
    // @RequestMapping(value="/error", method=RequestMethod.GET)
    // public String errorPage() {
    //     return "/error";
    //  }
    

}
