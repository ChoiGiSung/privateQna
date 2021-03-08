package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String createUser(User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String renderUserList(Model model) {
        List<User> getUsers = userService.findAll();
        model.addAttribute("users", getUsers);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String renderProfile(@PathVariable Long userId, Model model) {
        Optional<User> getUser = userService.findById(userId);
        model.addAttribute("user", getUser.orElseThrow(IllegalArgumentException::new));
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String renderUpdateForm(@PathVariable Long userId, Model model) {
        Optional<User> getUser = userService.findById(userId);
        model.addAttribute("user", getUser.orElseThrow(IllegalArgumentException::new));
        return "user/userUpdateForm";
    }

    @PutMapping("/update")
    public String userUpdate(User user, String newPassword) {
        if(userService.update(user, newPassword)){
            return "redirect:/";
        }
        return "user/userUpdateForm";
    }


}
