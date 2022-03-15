package com.dalhousie.MealStop.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dalhousie.MealStop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public com.dalhousie.MealStop.user.entity.User getUser(final HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = getJWTToken(request, user);
        com.dalhousie.MealStop.user.entity.User userEntity = userService.findUserByEmail(user.getUsername());
        userEntity.setToken(token);
        userEntity.setPassword(null);
        return userEntity;
    }

    private String getJWTToken(final HttpServletRequest request, User user) {

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        return "Bearer " + access_token;
    }
}
