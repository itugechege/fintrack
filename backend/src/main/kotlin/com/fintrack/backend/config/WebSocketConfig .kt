

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    /**
     * Configure message broker.
     * Messages with destinations starting with /app are routed to @MessageMapping methods.
     * Enables simple in-memory broker for /topic and /queue destinations.
     */
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.setApplicationDestinationPrefixes("/app")
        config.enableSimpleBroker("/topic", "/queue")
    }

    /**
     * Register STOMP endpoints for WebSocket connections.
     * SockJS is enabled for fallback options.
     */
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*") // Allow all origins (lock this down in production!)
            .withSockJS()
    }
}
