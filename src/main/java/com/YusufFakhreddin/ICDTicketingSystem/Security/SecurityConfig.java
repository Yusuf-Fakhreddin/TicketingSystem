package com.YusufFakhreddin.ICDTicketingSystem.Security;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
//    This class is used to configure security settings
//    such as authentication and authorization
//    for the application
//    create a user details manager to manage user through database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager= new JdbcUserDetailsManager(dataSource);

//      define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, password, active from users where user_id = ?");
//      define query to retrieve the user authorities by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id = ?");

//        return the jdbc user details manager
        return jdbcUserDetailsManager;
    }

//    create a filter chain to protect and assign roles for endpoints
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer-> configurer.requestMatchers(HttpMethod.GET, "/api/ticket/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/ticket/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/api/ticket/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/api/ticket/**").hasRole("ADMIN"));

//      use http basic authentication
        http.httpBasic(Customizer.withDefaults());
//        disable cross site request forger csrf
//        in general, not required for rest api since it is stateless and does not use cookies for authentication
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


}
