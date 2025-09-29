





@Configuration
@EnableWebSocketMessageBroker
class WebScoketConfig : WebSocketMessageBrokerConfigurer {

    override fun configurationMessageBroker(config: MessageBrokerRegistry){
        //Messages whose destination startw with /app go to @MessageMapping methods
        config.setapplicationDestinationPrefixes("/app")

        // Enable simple in-memory broker for /topic and /queue destinations
        config.enableSimpleBroker("/topic", "/queue")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // Endpoint for WebSocket connections
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*") // allow all origins (lock this down in prod!)
            .withSockJS()
    }
}