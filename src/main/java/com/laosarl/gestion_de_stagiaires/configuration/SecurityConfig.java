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
                                        .requestMatchers(HttpMethod.POST, "/internship")
                                        .requestMatchers(HttpMethod.GET, "/internship")
                                        .requestMatchers(HttpMethod.GET, "/internship/{internshipId}")
                                        .requestMatchers(HttpMethod.PATCH, "/internship/{internshipId}")
                                        .requestMatchers(HttpMethod.DELETE, "/internship/{internshipId}")
                                        .requestMatchers(HttpMethod.POST, "/internship/{internshipId}/accept")
                                        .requestMatchers(HttpMethod.POST, "/internship/{internshipId}/reject")
                                        .requestMatchers(HttpMethod.POST, "/internship/{internshipId}/assign-supervisor")

                                        .requestMatchers(HttpMethod.POST, "/documents/upload")
                                        .requestMatchers(HttpMethod.GET, "/documents/download/{id}")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/internship").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/internship").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/internship/{internshipId}").permitAll()
                                        .requestMatchers(HttpMethod.PATCH, "/internship/{internshipId}").permitAll()
                                        .requestMatchers(HttpMethod.DELETE, "/internship/{internshipId}").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/internship/{internshipId}/accept").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/internship/{internshipId}/reject").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/internship/{internshipId}/assign-supervisor").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/documents/upload").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/documents/download/{id}").hasRole("ADMIN")

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
                                .requestMatchers(HttpMethod.POST, "/register/supervisor").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/supervisors").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/supervisors/{id}").hasRole(Role.ADMIN.name())
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





