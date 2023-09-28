package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class MyConfiguration {

    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        RequestMatcher customMatcher = new AntPathRequestMatcher("/user/index");
//
//        http
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(customMatcher).authenticated() // Allow access to /user/index without authentication
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .anyRequest().authenticated()) // Require authentication for other requests
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout(LogoutConfigurer::permitAll);
//// Specify the custom login page URL if needed
//
//        return http.build();

        		http
                        .authorizeHttpRequests(authorizeRequests -> authorizeRequests

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                       .requestMatchers("/user/**").hasRole("USER")
                                .requestMatchers("/**").permitAll()
                       .anyRequest().authenticated())

                        .formLogin(formLogin -> {
                            formLogin
                                    .loginPage("/signin")
                                    .loginProcessingUrl("/dologin")
                                    .defaultSuccessUrl("/user/index")
//                                    .failureUrl("/failed")
                                    .permitAll();
                        })
                .logout(LogoutConfigurer::permitAll)

                        .csrf(csrf -> {
                            // Disable CSRF protection and its associated cookies
                            csrf.disable();
                        });                return http.build();

    }
}
