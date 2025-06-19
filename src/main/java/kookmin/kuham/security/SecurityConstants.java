package kookmin.kuham.security;

import java.util.List;

public class SecurityConstants {
    public static final List<String> PERMIT_ALL_PATTERNS = List.of(
            "/auth/login",
            "/auth/allUsers",
            "/posts/**",
            "/project/**",
            "/profile/**",
            "/auth/google/login",
            "/post/all-posts",
            "/auth/register",
            "/auth/callback",
            "/static/favicon.ico",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/webjars/**"
    );
}
