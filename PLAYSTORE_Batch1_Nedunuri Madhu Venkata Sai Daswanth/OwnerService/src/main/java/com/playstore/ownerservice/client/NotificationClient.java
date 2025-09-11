package com.playstore.ownerservice.client;

import com.playstore.ownerservice.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "notification-service", url = "http://localhost:8083/notify")
public interface NotificationClient {

    // Match NotificationController: GET /notify/user/{email}
    @GetMapping("/user/{email}")
    List<NotificationDTO> getNotifications(@PathVariable("email") String email);

    //  Match NotificationController: POST /notify/send
    @PostMapping("/send")
    String sendNotification(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("body") String body);
}
