package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenSpringRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String autHeader = request.getHeader("Authorization");

        if (Objects.nonNull(autHeader) && autHeader.startsWith("Bearer ")) {
            final String jwt = autHeader.substring(7);
            tokenSpringRepository.findByValue(jwt).ifPresent(tokenSpringRepository::delete);
        }
    }
}
