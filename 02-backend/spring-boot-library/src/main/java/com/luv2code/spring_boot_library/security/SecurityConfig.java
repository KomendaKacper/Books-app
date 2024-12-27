package com.luv2code.spring_boot_library.security;

import com.luv2code.spring_boot_library.dao.RoleRepository;
import com.luv2code.spring_boot_library.dao.UserRepository;
import com.luv2code.spring_boot_library.entity.AppRole;
import com.luv2code.spring_boot_library.entity.Role;
import com.luv2code.spring_boot_library.entity.User;
import com.luv2code.spring_boot_library.jwt.AuthEntryPointJwt;
import com.luv2code.spring_boot_library.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/api/**")
                );
        http.cors(withDefaults()) // Enable CORS
                .authorizeHttpRequests((requests) ->
                        requests
                                .requestMatchers("/api/books").permitAll() // Public API endpoint
                                .requestMatchers("/api/books/secure/**").authenticated()
                                .requestMatchers("api/csrf-token").permitAll()
                                .requestMatchers("/api/auth/public/**").permitAll()
                                .anyRequest().authenticated() // All other endpoints require authentication
                );
            http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            http.formLogin(withDefaults()); // Default form login
            http.httpBasic(withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}
