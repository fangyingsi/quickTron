package com.quicktron.business.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class QuickTSocketServerConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

    public QuickTSocketServerConfig() {   };

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println("registed!");

        WebSocketHandlerRegistration registration = webSocketHandlerRegistry.addHandler(new QuickTWebSocketHandler(), "/websocket","sockjs/websocket");
        SockJsServiceRegistration sockJS = registration.withSockJS();
        // 添加拦截器
        registration.addInterceptors(new QuickTSocketInterceptor()).setAllowedOrigins("*");
    }
}
