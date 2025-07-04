package com.laosarl.gestion_de_stagiaires.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gestionstagiaire.cors")
public class CorsProperties {
    private String[] allowedOrigins;
    private String allowedMethods;
    private String allowedHeaders;
}