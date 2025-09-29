package com.fintrack.backend.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/", "/graphql", "/graphiql", "/css/**", "/js/**").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form.loginPage("/login").permitAll()
            }
            .oauth2Login { oauth2 ->
                oauth2.loginPage("/login") // custom login page
            }
            .logout { logout ->
                logout.logoutSuccessUrl("/").permitAll()
            }

        return http.build()
    }
}