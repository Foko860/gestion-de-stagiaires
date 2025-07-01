package com.laosarl.gestion_de_stagiaires.configuration;

import com.laosarl.gestion_de_stagiaires.Service.LogoutService;
import com.laosarl.gestion_de_stagiaires.configuration.filter.JwtAuthenticationFilter;
import com.laosarl.gestion_de_stagiaires.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;

    @Bean
    @Order(0)
    public SecurityFilterChain healthEndpoints(HttpSecurity http) throws Exception {
        return http.securityMatcher("/actuator/health/**", "/actuator/swagger-ui")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }


    @ConditionalOnProperty(
            name = "spring.security.enabled",
            havingValue = "true",
            matchIfMissing = true)
    @Bean
    @Order(1)
    public SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {
        log.info("Public filter chain start");
        return http.securityMatchers(
                        requestMatcherConfigurer ->
                                requestMatcherConfigurer
                                        .requestMatchers(HttpMethod.POST, "/auth/login")
                                        .requestMatchers(HttpMethod.POST, "/register/supervisor")
                                        .requestMatchers(HttpMethod.POST, "/internship")
                                        .requestMatchers(HttpMethod.POST, "/internship/{id}/accept")
                                        .requestMatchers(HttpMethod.POST, "/internship/{id}/reject")
                                        .requestMatchers(HttpMethod.GET, "/internship")
                                        .requestMatchers(HttpMethod.PATCH, "/internship")
                                        .requestMatchers(HttpMethod.PATCH, "/internship/{id}")
                                        .requestMatchers(HttpMethod.GET, "/internship/{id}")
                                        .requestMatchers(HttpMethod.GET, "/supervisors")
                                        .requestMatchers(HttpMethod.GET, "/supervisors/{id}")
                                        .requestMatchers(HttpMethod.POST, "/documents/upload")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/register/supervisor").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.POST, "/internship").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/internship").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.GET, "/internship/{id}").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.DELETE, "/internship").permitAll()
                                        .requestMatchers(HttpMethod.PATCH, "/internship").permitAll()
                                        .requestMatchers(HttpMethod.PATCH, "/internship/{id}").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/internship/{id}/accept").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.POST, "/internship/{id}/reject").hasRole(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.GET, "/supervisors").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/supervisors/{id}").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/documents/upload").permitAll()
                )
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) ->
                        authz
                                .requestMatchers(HttpMethod.GET, "/auth/user").authenticated()
                                .anyRequest().denyAll()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }

    @ConditionalOnProperty(name = "spring.security.enabled", havingValue = "false")
    @Bean
    public SecurityFilterChain disabledSecurityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                .build();
    }
}





