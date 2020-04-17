package com.example.sweater.controller;


import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repository.MessageRepo;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

@Controller // This means that this class is a Controller
//@RequestMapping(path = "/demo")
// This means URL's start with /demo (after Application path)public class GreetingController

public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @PostMapping("/allMessages")
    public String addNewMessage(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "empty") String text,
            @RequestParam(required = false, defaultValue = "none") String tag,
            Model model) {
        //  public @ResponseBody - returns a JSON or XML with the users
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Message m = new Message(text, tag, user);
        messageRepo.save(m);
        model.addAttribute("messages", messageRepo.findAll());
        return "allMessages";
    }

    @PostMapping("filterMessageByTag")
    public String filter(@RequestParam String tagFilter, Model model) {
        Iterable<Message> messageList;

        if (tagFilter != null && !tagFilter.isEmpty()) {
            messageList = messageRepo.findByTag(tagFilter);
        } else {
            messageList = messageRepo.findAll();
        }
        model.addAttribute("messages", messageList);
        return "allMessages";
    }

    @GetMapping("/allMessages")
    public String showAll(Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        return "allMessages";
    }

    @GetMapping(path = "/test")
    public String greeting() {
        return "test";
    }
/*
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
*/

    @GetMapping(path = "/")
    public String mainPag() {
        return "greeting";
    }
}