package com.playstore.ownerservice.controller;

import com.playstore.ownerservice.entity.Owner;
import com.playstore.ownerservice.entity.Application;
import com.playstore.ownerservice.dto.AuthRequest;
import com.playstore.ownerservice.dto.AuthResponse;
import com.playstore.ownerservice.dto.NotificationDTO;
import com.playstore.ownerservice.service.OwnerService;
import com.playstore.ownerservice.service.ApplicationService;
import com.playstore.ownerservice.client.NotificationClient;
import com.playstore.ownerservice.security.JwtUtil;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;
    private final ApplicationService appService;
    private final NotificationClient notificationClient;
    private final JwtUtil jwtUtil;

    public OwnerController(OwnerService ownerService,
                           ApplicationService appService,
                           NotificationClient notificationClient,
                           JwtUtil jwtUtil) {
        this.ownerService = ownerService;
        this.appService = appService;
        this.notificationClient = notificationClient;
        this.jwtUtil = jwtUtil;
    }

    //  Register form
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("owner", new Owner());
        return "register";
    }

    //  Handle registration
    @PostMapping("/register")
    public String register(@ModelAttribute("owner") @Valid Owner owner, Model model) {
        try {
            ownerService.register(owner);
            model.addAttribute("success", "Registered successfully! Please login.");
            return "login";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    //  Login form
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("owner", new Owner());
        return "login";
    }

    //  Handle login (store owner in HttpSession)
    @PostMapping("/login")
    public String login(@RequestParam String emailOrPhone,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Owner owner = ownerService.login(emailOrPhone, password);

            // store owner in session
            session.setAttribute("owner", owner);

            // Generate JWT (optional, for APIs)
            String token = jwtUtil.generateToken(owner.getEmail(), "ROLE_OWNER");
            session.setAttribute("token", token);

            return "redirect:/owner/dashboard";
        } catch (RuntimeException ex) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    //  Dashboard (only accessible if logged in)
 //  Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Owner owner = (Owner) session.getAttribute("owner");
        if (owner == null) {
            return "redirect:/owner/login"; // if no session, go to login
        }

        List<Application> apps = appService.getAppsByOwner(owner.getId());
        long totalDownloads = appService.getTotalDownloadsByOwner(owner.getId());
        List<NotificationDTO> notifications = notificationClient.getNotifications(owner.getEmail());

        model.addAttribute("owner", owner);
        model.addAttribute("apps", apps);
        model.addAttribute("totalDownloads", totalDownloads);
        model.addAttribute("notifications", notifications);

        return "dashboard";
    }

    //  Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session completely
        return "redirect:/owner/login"; // always redirect to login
    }

    // REST API login (for Postman/mobile with JWT)
    @PostMapping("/api/login")
    @ResponseBody
    public AuthResponse apiLogin(@RequestBody AuthRequest request) {
        Owner owner = ownerService.login(request.getEmailOrPhone(), request.getPassword());
        String token = jwtUtil.generateToken(owner.getEmail(), "ROLE_OWNER");
        return new AuthResponse(token);
    }
}
