package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final UserService userService;

    public AnswerController(AnswerService answerService, UserService userService) {
        this.answerService = answerService;
        this.userService = userService;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        answerService.write(user, contents, questionId);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerService.findById(id);
        userService.checkSameUser(user, answer.getWriter().getId());
        answerService.delete(id);

        return "redirect:/";
    }

}
