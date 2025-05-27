package kookmin.kuham.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kookmin.kuham.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AntPathMatcher pathMatcher =  new AntPathMatcher();

    private boolean permitPath(String path){
        return SecurityConstants.PERMIT_ALL_PATTERNS.stream().anyMatch(permitPath -> pathMatcher.match(permitPath, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        if(permitPath(requestUrl)){
            filterChain.doFilter(request,response);
            return;
        }
        String requestHeader = request.getHeader("Authorization");



    }
}
