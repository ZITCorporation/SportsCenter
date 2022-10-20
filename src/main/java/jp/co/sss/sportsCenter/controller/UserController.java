package jp.co.sss.sportsCenter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.sportsCenter.entity.User;
import jp.co.sss.sportsCenter.form.UserForm;
import jp.co.sss.sportsCenter.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(types = User.class)
public class UserController {
    @Autowired
    UserRepository repository;

    // ---------------------------------------------------------
    // Find 検索
    @RequestMapping("/users/findAll")
    public String showUserList(Model model) {
        model.addAttribute("users", repository.findAll());
        return "/users/search/user_list"; // 管理者権限を持つ、すべてのユーザー詳細画面のhtmlファイルのパス
    }

    @RequestMapping(value = "/users/detail")
    public String showUser(HttpSession session, Model model) {
        int id = ((User) session.getAttribute("user")).getUserId();
        User user = repository.getReferenceById(id);
        model.addAttribute("user", user);
        return "/users/search/user_detail"; // ユーザー詳細画面のhtmlファイルのパス
    }

    // ---------------------------------------------------------
    // Create 新規登録
    @RequestMapping(value = "/users/create/input", method = RequestMethod.GET)
    public String createInput() {
        return "/users/create/input"; // 新規登録入力画面のhtmlファイルのパス
    }

    @RequestMapping(value = "/users/create/confirm", method = RequestMethod.POST)
    public String createComplete(UserForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setPost(form.getPost());
        user.setDomicile(form.getDomicile());
        user.setAuthority(0);
        repository.save(user);
        return "/users/create/complete"; // ユーザー新規登録成功画面のhtmlファイルのパス
    }

    // ---------------------------------------------------------
    // Update 更新
    @RequestMapping(value = "/users/update/input")
    public String updateInput() {
        return "/users/update/input"; // ユーザー情報更新の入力画面のhtmlファイルのパス
    }

    @RequestMapping(value = "/users/update/confirm", method = RequestMethod.POST)
    public String updateComplete(HttpSession session, UserForm form) {
        int id = ((User) session.getAttribute("user")).getUserId();
        User user = repository.getReferenceById(id);
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setPost(form.getPost());
        user.setDomicile(form.getDomicile());
        user.setAuthority(form.getAuthority());
        repository.save(user);
        session.setAttribute("user", user);
        return "/users/update/complete"; // ユーザー情報更新成功画面のhtmlファイルのパス
    }

    // ---------------------------------------------------------
    // Delete 削除
    @RequestMapping(value = "/users/delete/confirm", method = RequestMethod.GET)
    public String delete() {
        return "/users/delete/confirm"; // ユーザー削除確認画面のhtmlファイルのパス
    }

    @RequestMapping(value = "/users/delete/complete", method = RequestMethod.GET)
    public String deleteCompelete(HttpSession session) {
        int id = ((User) session.getAttribute("user")).getUserId();
        repository.deleteById(id);
        return "/users/delete/complete"; // ユーザー削除成功画面のhtmlファイルのパス
    }

}
