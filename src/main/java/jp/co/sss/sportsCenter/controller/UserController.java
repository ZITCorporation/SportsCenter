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

    // ---------------------------------------------------------
    // Find 検索
    // @RequestMapping("/users/findAll")
    // public String showUserList(HttpSession session, Model model) {
    // User user = (User) session.getAttribute("user");
    // if (user.getAuthority() == 1) {
    // model.addAttribute("users",
    // userRepository.findAll(Sort.by("userId").descending()));
    // return "/users/search/user_list"; // 管理者権限を持つ、すべてのユーザー詳細画面のhtmlファイルのパス
    // } else {
    // return "/error";
    // }
    // }

    // //
    // @RequestMapping(value = "/users/detail")
    // public String showUser(HttpSession session, Model model) {
    // // int id = ((User) session.getAttribute("user")).getUserId();
    // // Optional<User> op = userRepository.findById(id);
    // // User user = op.get();
    // // model.addAttribute("user", user);
    // return "/users/search/user_detail"; // ユーザー詳細画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/detail/{id}")
    // public String showUserById(HttpSession session, Model model,
    // @PathVariable("id") int id) {
    // Optional<User> op = userRepository.findById(id);
    // User user = op.get();
    // model.addAttribute("user", user);
    // return "/users/search/user_detail"; // ユーザー詳細画面のhtmlファイルのパス
    // }

    // // ---------------------------------------------------------
    // // Create 新規登録
    // @RequestMapping(value = "/users/create/input", method = RequestMethod.GET)
    // public String createInput() {
    // return "/users/create/input"; // 新規登録入力画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/create/confirm", method = RequestMethod.POST)
    // public String createConfirm(UserForm form, Model model) {
    // User user = new User();
    // user = form2User(user, form);
    // model.addAttribute("user", user);
    // return "/users/create/confirm"; // ユーザー新規登録確認画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/create/back", method = RequestMethod.POST)
    // public String backToCreateInput(UserForm form, Model model) {
    // User user = new User();
    // user = form2User(user, form);
    // model.addAttribute("user", user);
    // return "/users/create/input"; // ユーザー新規登録入力画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/create/complete", method =
    // RequestMethod.POST)
    // public String createComplete(UserForm form) {
    // User user = new User();
    // user = form2User(user, form);
    // userRepository.save(user);
    // return "/users/create/complete"; // ユーザー新規登録成功画面のhtmlファイルのパス
    // }

    // // ---------------------------------------------------------
    // // Update 更新
    // @RequestMapping(value = "/users/update/input")
    // public String updateInput() {
    // return "/users/update/input"; // ユーザー情報更新の入力画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/update/confirm", method = RequestMethod.POST)
    // public String updateConfirm(HttpSession session, UserForm form, Model model)
    // {
    // int id = ((User) session.getAttribute("user")).getUserId();
    // User user = userRepository.getReferenceById(id);
    // user = form2User(user, form);
    // user.setAuthority(form.getAuthority());
    // model.addAttribute("user", user);
    // return "/users/update/confirm"; // ユーザー情報更新確認画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/update/back", method = RequestMethod.POST)
    // public String backToUpdateInput(UserForm form, Model model) {
    // User user = new User();
    // user = form2User(user, form);
    // model.addAttribute("user_back", user);
    // return "/users/update/input"; // ユーザー情報更新入力画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/update/complete", method =
    // RequestMethod.POST)
    // public String updateComplete(HttpSession session, UserForm form) {
    // int id = ((User) session.getAttribute("user")).getUserId();
    // User user = userRepository.getReferenceById(id);
    // user = form2User(user, form);
    // userRepository.save(user);
    // session.setAttribute("user", user);
    // return "/users/update/complete"; // ユーザー情報更新成功画面のhtmlファイルのパス
    // }

    // // ---------------------------------------------------------
    // // Delete 削除
    // @RequestMapping(value = "/users/delete/confirm", method = RequestMethod.GET)
    // public String delete() {
    // return "/users/delete/confirm"; // ユーザー削除確認画面のhtmlファイルのパス
    // }

    // @RequestMapping(value = "/users/delete/complete", method = RequestMethod.GET)
    // public String deleteCompelete(HttpSession session) {
    // int id = ((User) session.getAttribute("user")).getUserId();
    // userRepository.deleteById(id);
    // return "/users/delete/complete"; // ユーザー削除成功画面のhtmlファイルのパス
    // }

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
    public String usersUser(HttpSession session, Model model) {

        // 権限チェック

        List<User> userList = userRepository.findAll(Sort.by("userId").descending());
        model.addAttribute("userList", userList); // rmからuserListまで
        return "/user/user_list";
    }

    // ユーザー詳細
    @GetMapping("/user/userDetail/{id}")
    public String usersUserDetail(@PathVariable("id") int id, HttpSession session, Model model) {

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
}
