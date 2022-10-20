package jp.co.sss.sportsCenter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    // Find
    @RequestMapping("/users/findAll")
    public String showUserList(Model model) {
        model.addAttribute("users", repository.findAll());
        return "/users/user_list";
    }

    // @RequestMapping(value = "/users/getOne/{id}")
    // public String showUser(@PathVariable int id, Model model) {
    // model.addAttribute("user", repository.getReferenceById(id));
    // return "/users/user_detail";
    // }

    @RequestMapping(value = "/users/detail")
    public String showUser() {
        return "/users/user_detail";
    }

    // ---------------------------------------------------------
    // Create
    @RequestMapping(value = "/users/create/input", method = RequestMethod.GET)
    public String createInput() {
        return "/users/create/input";
    }

    @RequestMapping(value = "/users/create/complete", method = RequestMethod.POST)
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
        return "redirect:/users/getOne/" + user.getId();
    }

    // ---------------------------------------------------------
    // Update
    @RequestMapping(value = "/users/update/input")
    public String updateInput() {
        return "/users/update/input";
    }

    @RequestMapping(value = "/users/update/confirm", method = RequestMethod.POST)
    public String updateComplete(HttpSession session, UserForm form) {
        User user = repository.getReferenceById(((User) session.getAttribute("user")).getId());
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setPost(form.getPost());
        user.setDomicile(form.getDomicile());
        user.setAuthority(form.getAuthority());
        repository.save(user);
        return "redirect:/users/detail";
    }

    // ---------------------------------------------------------
    // Delete
    @RequestMapping(value = "/users/delete/confirm/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        return "/users/delete/confirm";
    }

    @RequestMapping(value = "/users/delete/complete/{id}", method = RequestMethod.GET)
    public String deleteCompelete(@PathVariable int id) {
        repository.deleteById(id);
        return "/users/delete/complete";
    }

}
