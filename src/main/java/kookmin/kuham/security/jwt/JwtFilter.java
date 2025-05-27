package kookmin.kuham.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kookmin.kuham.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        if(permitPath(requestUrl)){
            filterChain.doFilter(request,response);
            return;
        }
        String requestHeader = request.getHeader("Authorization");
        String token = null;


        //request header 에서 Bearer 토큰 추출
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
        }


        if(token != null){
            try{
                Claims claims = jwtTokenProvider.validateToken(token);
                String userId = claims.getSubject();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                // 토큰이 유효하지 않은 경우
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request,response);


    }
}
