package kookmin.kuham.chat.config;

import kookmin.kuham.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public WebSocketConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void configureMessageBroker(MessageBrokerRegistry config) {
        //서버가 클라이언트에게 메세지를 보낼 때 사용하는 주소, ex)클라이언트는 /topic/chatroom/room123을 구독해야 이 메시지를 받을 수 있음.
        config.enableSimpleBroker("/topic");
        //클라이언트는 항상 /app/~~로 시작하는 주소로 메시지를 보내야 서버가 받을 수 있어.
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //클라이언트가 실제 WebSocket 연결을 시도할 주소를 등록하는 것
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration config) {
        // WebSocket 연결 시 JWT 인증을 위한 인터셉터 등록
        config.interceptors(new JwtChannelInterceptor(jwtTokenProvider));
    }
}
