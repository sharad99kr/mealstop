package com.dalhousie.MealStop.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.dalhousie.MealStop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static com.dalhousie.MealStop.common.CommonConstants.BEARER;
import static com.dalhousie.MealStop.common.CommonConstants.ROLES;
import static com.dalhousie.MealStop.common.UrlConstants.API_VERSION_1;
import static com.dalhousie.MealStop.common.UrlConstants.USER_URL;

@Controller
@RequestMapping(API_VERSION_1)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiresAt}")
    private String expiresAt;

    @PostMapping(USER_URL)
    public com.dalhousie.MealStop.user.entity.User getUser(final HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = getJWTToken(request, user);
        com.dalhousie.MealStop.user.entity.User userEntity = userService.findUserByEmail(user.getUsername());
        userEntity.setToken(token);
        userEntity.setPassword(null);
        return userEntity;
    }

    private String getJWTToken(final HttpServletRequest request, User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        JWTCreator.Builder jwt = JWT.create();
        jwt.withSubject(user.getUsername());
        jwt.withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(expiresAt)));
        jwt.withIssuer(request.getRequestURL().toString());
        jwt.withClaim(ROLES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String access_token = jwt.sign(algorithm);
        return BEARER + access_token;
    }
}
