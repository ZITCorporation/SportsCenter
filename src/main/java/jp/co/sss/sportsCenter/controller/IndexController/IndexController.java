
package jp.co.sss.sportsCenter.controller.IndexController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("sample", "動作確認です");
        return "index";
    }
}
