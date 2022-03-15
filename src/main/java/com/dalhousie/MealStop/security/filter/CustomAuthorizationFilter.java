package com.dalhousie.MealStop.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dalhousie.MealStop.security.config.WhitelistUrlConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final static String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //If request is called for login, pass it to the next filter.
        if (request.getServletPath().equals(WhitelistUrlConstants.LOGIN_URL)) {
            filterChain.doFilter(request, response);
        } else if(request.getServletPath().equals(WhitelistUrlConstants.LOGOUT_URL)) {
            SecurityContextHolder.clearContext();
        }
        else {
            String authHeader = (String) request.getAttribute(AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith(BEARER)) {
                try {
                    String token = authHeader.substring(BEARER.length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decoded = verifier.verify(token);
                    String email = decoded.getSubject();
                    String[] roles = decoded.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(roles[0]));
                    var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap();
                    error.put("error_message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
