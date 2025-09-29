package com.fintrack.backend.configuration

import org.springframework.context.ApplicationContext
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.xml.xsd.XsdSchema

@EnableWs
@Configuration
class WebServiceConfig {

    @Bean
    fun messageDispatcherServlet(context: ApplicationContext): ServletRegistrationBean<MessageDispatcherServlet> {
        val servlet = MessageDispatcherServlet()
        servlet.setApplicationContext(context)
        servlet.setTransformWsdlLocations(true)
        return ServletRegistrationBean(servlet, "/ws/*") // SOAP endpoint
    }

    @Bean
    fun usersSchema(): XsdSchema {
        return SimpleXsdSchema(ClassPathResource("users.xsd"))
    }
}