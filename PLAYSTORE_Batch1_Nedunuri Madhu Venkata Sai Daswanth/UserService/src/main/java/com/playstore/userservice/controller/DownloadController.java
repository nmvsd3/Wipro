package com.playstore.userservice.controller;

import com.playstore.userservice.entity.Download;
import com.playstore.userservice.service.UserAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

// Controller for handling user download history (web views)
@Controller
@RequestMapping("/user/downloads")
public class DownloadController {

    private final UserAppService appService;

    // Constructor injection of UserAppService
    public DownloadController(UserAppService appService) {
        this.appService = appService;
    }

    // Show the logged-in user's downloaded apps
    @GetMapping
    public String myDownloads(@SessionAttribute("user") com.playstore.userservice.entity.User user,
                              Model model) {
        List<Download> downloads = appService.getUserDownloads(user.getEmail());
        model.addAttribute("downloads", downloads);
        return "my-downloads"; // returns Thymeleaf/JSP template view
    }
}
