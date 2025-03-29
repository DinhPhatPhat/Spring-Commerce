package space.dinhphatphat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login", "/api/user/register", "/api/user/forgot-password", "/api/user/change-password", "/api/user/logout").permitAll()
                        .requestMatchers("/api/story/**").permitAll()
                        .requestMatchers("/user/login", "/user/register", "/user/forgot-password", "/user/change-password", "/user/verify").permitAll()
                        .requestMatchers("/story/**").permitAll()
                        .requestMatchers("/", "/info", "/user").permitAll()
                        .requestMatchers("/css/**", "/javascript/**", "/image/**", "/vendor/**" ).permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
