package com.example.staffapi.config;

public interface ConfigConstants {

    String[] PERMIT_ALL_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html/**"
    };

    String OAUTH_SCHEME = "oAuth";
    String TOKEN_URL = "%s/realms/%s/protocol/openid-connect/token";
    String API_PREFIX = "api";
}
