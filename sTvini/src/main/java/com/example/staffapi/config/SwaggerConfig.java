package com.example.staffapi.config;


import com.example.staffapi.config.properties.KeycloakProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import static com.example.staffapi.config.ConfigConstants.OAUTH_SCHEME;
import static com.example.staffapi.config.ConfigConstants.TOKEN_URL;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {


    @Bean
    OpenAPI customOpenApi(KeycloakProperties keycloakProperties) {
        var passwordFlow = new OAuthFlow()
                .tokenUrl(String.format(TOKEN_URL,
                        keycloakProperties.getAuthServerUrl(), keycloakProperties.getRealm()))
                .scopes(new Scopes());

        var securityScheme =  new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows().password(passwordFlow));

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME, securityScheme))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME));
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowCredentials(false);
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }


}