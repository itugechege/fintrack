package com.fintrack.backend.configuration


@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http
            .authorizeHttpRequest { auth ->
            auth
                .requestMatchers("/", "/graphql", "/graphiql", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            }
            .formLogin { from ->
                form.loginPage("/login").permitAll()
            }
            .oauth2Login{oauth2 ->
                oauth2.loginPage("login")//custom login page

            }.logout { logout ->
                logout.logoutSuccessUrl("/").permitAll()

            }
        return http.build()
    }
}