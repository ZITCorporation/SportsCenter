package jp.co.sss.sportsCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.sportsCenter.entity.User;
import jp.co.sss.sportsCenter.form.UserForm;
import jp.co.sss.sportsCenter.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    UserRepository repository;

    // ---------------------------------------------------------
    // Find
    @RequestMapping("/users/findAll")
    public String showUserList(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users/user_list.html";
    }

    @RequestMapping(value = "/users/getOne/{id}")
    public String showUser(@PathVariable int id, Model model) {
        model.addAttribute("user", repository.getReferenceById(id));
        return "/users/user_detail";
    }

    // ---------------------------------------------------------
    // Create
    @RequestMapping(value = "/users/create/input", method = RequestMethod.GET)
    public String createInput() {
        return "users/create/input";
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
    @RequestMapping(value = "/users/update/input/{id}")
    public String updateInput(@PathVariable int id, Model model) {
        model.addAttribute("user", repository.getReferenceById(id));
        return "/users/update/input";
    }

    @RequestMapping(value = "/users/update/complete/{id}", method = RequestMethod.POST)
    public String updateComplete(@PathVariable int id, UserForm form) {
        User user = repository.getReferenceById(id);
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
    // Delete
}
