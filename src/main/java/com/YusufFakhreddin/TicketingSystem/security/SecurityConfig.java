package com.YusufFakhreddin.TicketingSystem.security;

import com.YusufFakhreddin.TicketingSystem.errorHandling.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SecurityConfig {
//    This class is used to configure security settings
//    such as authentication and authorization
//    for the application
//    create a user details manager to manage user through database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager= new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, active from users where username = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, role from roles where username = ?");

//        return the jdbc user details manager
        return jdbcUserDetailsManager;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                CustomErrorResponse errorResponse = new CustomErrorResponse(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this resource");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(errorResponse);

                response.getOutputStream().println(json);
            }
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                CustomErrorResponse errorResponse = new CustomErrorResponse(HttpServletResponse.SC_UNAUTHORIZED,"You are not authenticated");

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(errorResponse);

                response.getOutputStream().println(json);
            }
        };
    }

//    create a filter chain to protect and assign roles for endpoints
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
    http.authorizeRequests(configurer -> configurer
                    .requestMatchers(HttpMethod.GET, "/api/ticket/**").hasAnyRole("EMPLOYEE", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/ticket/**").hasAnyRole("EMPLOYEE", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/ticket/**").hasAnyRole("EMPLOYEE", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/ticket/**").hasAnyRole("EMPLOYEE", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/user/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/user/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/user/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN")
                    .anyRequest().authenticated())
            .exceptionHandling(configurer -> configurer
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint));


//      use http basic authentication
        http.httpBasic(Customizer.withDefaults());
//        disable cross site request forger csrf
//        in general, not required for rest api since it is stateless and does not use cookies for authentication
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


}
