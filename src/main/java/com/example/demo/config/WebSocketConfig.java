package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // STOMP 프로토콜을 사용하여 메시지를 전송할 수 있는 엔드포인트를 설정합니다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트를 등록합니다. SockJS 지원 연결을 허용합니다.
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    // 메시지 브로커의 설정을 구성합니다.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트에서 "/topic"으로 시작하는 주소로 메시지를 구독할 수 있도록 설정합니다.
        registry.enableSimpleBroker("/topic");
        // 클라이언트에서 "/app"으로 시작하는 주소로 메시지를 전송할 수 있도록 설정합니다.
        registry.setApplicationDestinationPrefixes("/app");
    }
}
