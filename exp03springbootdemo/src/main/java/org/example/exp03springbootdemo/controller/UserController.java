package org.example.exp03springbootdemo.controller;

import org.example.exp03springbootdemo.pojo.User;
import org.example.exp03springbootdemo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = {"/","/list"})
    public String list(Model model){
        List<User> list = userService.getUsers();
        model.addAttribute("list", list);
        return "list";
    }

    @GetMapping("/add")
    public String add(){
        return "add";
    }

    @PostMapping("/add")
    public String add(User user){
        userService.addUser(user);
        return "redirect:/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update")
    public String update(User user){
        userService.updateUser(user);
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        userService.deleteUser(id);
        return "redirect:/list";
    }
}
