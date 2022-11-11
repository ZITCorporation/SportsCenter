
package jp.co.sss.sportsCenter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.sportsCenter.entity.User;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {
    
// cd sportsCenter
// mvn spring-boot:run

    // ホーム
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

    // エラー
    @RequestMapping(value="/error", method=RequestMethod.GET)
    public String errorPage() {
        return "/error";
     }
    

    @RequestMapping("/listFacilities")
    public String listFacilities() {
        return "/facility/listFacilities";
    }
    
    // アーチェリー
    @RequestMapping("/archeryExplanation")
    public String archeryExplanation() {
        return "/facility/archeryExplanation";
    }
    @RequestMapping("/archery")
    public String archery() {
        return "/facility/archery";
    }
    
    // ジム
    @RequestMapping("/gymExplanation")
    public String gymExplanation() {
        return "/facility/gymExplanation";
    }
    @RequestMapping("/gym")
    public String gym() {
        return "/facility/gym";
    }
    
    // テニス
    @RequestMapping("/tennisExplanation")
    public String tennisExplanation() {
        return "/facility/tennisExplanation";
    }
    @RequestMapping("/tennis")
    public String tennis() {
        return "/facility/tennis";
    }
    
    // バスケットボール
    @RequestMapping("/basketballExplanation")
    public String basketballExplanation() {
        return "/facility/basketballExplanation";
    }
     @RequestMapping("/basketball")
    public String basketball() {
        return "/facility/basketball";
    }
    
    // プール
    @RequestMapping("/poolExplanation")
    public String poolExplanation() {
        return "/facility/poolExplanation";
    }
    @RequestMapping("/pool")
    public String pool() {
        return "/facility/pool";
    }

}
