package com.example.demo.controller;

import com.example.demo.dto.NotificationMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * 클라이언트에게 WebSocket 을 통해 알림 메시지를 전송하는 API
     *
     * @param message 알림 메시지 DTO
     * @return 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationMessage message) {
        // WebSocket 을 통해 클라이언트에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/notifications", message);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
