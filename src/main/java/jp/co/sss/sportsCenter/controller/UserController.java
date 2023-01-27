package jp.co.sss.sportsCenter.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jp.co.sss.sportsCenter.entity.User;
import jp.co.sss.sportsCenter.form.UserForm;
import jp.co.sss.sportsCenter.repository.UserRepository;

import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(types = User.class)
public class UserController {
    @Autowired
    UserRepository userRepository;

    private User form2User(User user, UserForm form) {
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setPost(form.getPost());
        user.setDomicile(form.getDomicile());
        user.setAuthority(form.getAuthority());
        return user;
    }

    // 管理者・ユーザー一覧
    @GetMapping("/user/findAll")
    public String listAllUser(HttpSession session, Model model) {

        // 権限チェック

        List<User> userList = userRepository.findAll(Sort.by("userId").descending());
        model.addAttribute("userList", userList); // rmからuserListまで
        return "/user/user_list";
    }

    // 当前ユーザー詳細
    @GetMapping("/user/detail")
    public String userDetail(HttpSession session, Model model) {
        int id = (int) session.getAttribute("id");
        Optional<User> op = userRepository.findById(id);
        User user = op.get();
        model.addAttribute("user", user); // rmからuserまで
        return "/user/user_detail";
    }

    // 指定ユーザー詳細
    @GetMapping("/user/detail/{id}")
    public String userDetailById(@PathVariable("id") int id, HttpSession session, Model model) {

        // 権限について、管理者はすべてのユーザーをアクセスができ、普通ユーザーは自分のIDで検索できると考えばいい。

        Optional<User> op = userRepository.findById(id);
        User user = op.get();
        model.addAttribute("user", user); // rmからuserListまで
        return "/user/user_detail";
    }

    // 新規ユーザー入力
    @GetMapping("/user/create")
    public String createInput(HttpSession session) {
        session.setAttribute("userStatus", "create");
        return "/user/input";
    }

    // 新規ユーザー確認
    @PostMapping("/user/create/confirm")
    public String createConfirm(@ModelAttribute("form") @Valid UserForm form, Model model, HttpSession session) {
        if (form.getAuthority() == null) {
            form.setAuthority(0);
        }
        model.addAttribute("form", form);
        return "/user/confirm";
    }

    // 新規ユーザー確認から、入力へ戻る
    @PostMapping("/user/create/back")
    public String backToCreateInput(@ModelAttribute("form") @Valid UserForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/user/input";
    }

    // ユーザー新規完了
    @PostMapping("/user/create/complete")
    public String createComplete(@ModelAttribute("form") @Valid UserForm form, HttpSession session) {
        User user = new User();
        user = form2User(user, form);
        userRepository.save(user);
        return "/user/complete";
    }

    // ユーザー変更
    @PostMapping("/user/update/input")
    public String userUpdate(@Valid UserForm form, HttpSession session, Model model) {
        session.setAttribute("userStatus", "update");
        model.addAttribute("form", form);
        return "/user/input";
    }

    // ユーザー変更確認
    @PostMapping("/user/update/confirm")
    public String userUpdateConfirm(@Valid UserForm form, HttpSession session, Model model) {
        if (form.getAuthority() == null) {
            form.setAuthority(0);
        }
        model.addAttribute("form", form);
        return "/user/confirm";
    }

    // ユーザー変更確認から戻る
    @PostMapping("/user/update/back")
    public String userUpdateConfirmBack(@Valid UserForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/user/input";
    }

    // ユーザー変更完了
    @PostMapping("/user/update/complete")
    public String userUpdateComplete(@Valid UserForm form, HttpSession session, Model model) {
        int id = form.getUserId();
        Optional<User> op = userRepository.findById(id);
        User user = op.get();
        user = form2User(user, form);
        userRepository.save(user);
        return "/user/complete";
    }

    // ユーザー削除
    @PostMapping("/user/delete")
    public String userDelete(HttpSession session, HttpServletRequest request) {

        // 権限について、管理者はすべてのユーザーを削除ができ、普通ユーザーは自分のIDで削除できると考えばいい。

        session.setAttribute("userStatus", "delete");
        String index = request.getParameter("index");
        userRepository.deleteById(Integer.parseInt(index));
        return "/user/complete";
    }

    // ユーザー削除完了
    @GetMapping("/user/delete/complete")
    public String userDeleteComplete(HttpSession session) {
        session.setAttribute("userStatus", "delete");
        return "/user/complete";
    }
}
