package com.cathy.shopping.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${settings.cors_origin}")
    private String FRONT_END_HOST;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUsers = new InMemoryUserDetailsManager(); // 預設使用 BCrypt 若沒編碼則認證會失敗
        inMemoryUsers.createUser(User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build());
        inMemoryUsers.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN").build());
        return inMemoryUsers;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
//                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 可不加
//                .cors(cors -> cors.disable())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .logout(withDefaults())
                .authorizeHttpRequests( request -> request
                    .requestMatchers( "/api/v1/employees").hasRole("ADMIN")
                    .requestMatchers("/api/v1/employees/check-email").permitAll()
                    .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedOrigins(List.of(FRONT_END_HOST));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
