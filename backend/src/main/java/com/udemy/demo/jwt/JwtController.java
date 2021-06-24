package com.udemy.demo.jwt;

import com.udemy.demo.configuration.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwtutils jwtutils;

    @Autowired
    private MyUserDetailService service;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request,
                                                       HttpServletResponse response) throws Exception {

        authenticate(request.getEmail(), request.getPassword());
        MyUserDetailService.UserPrincipal principal = (MyUserDetailService.UserPrincipal) service.loadUserByUsername(request.getEmail());

        String token = jwtutils.generateToken(principal);

        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(
                principal.getUser().getId(),
                principal.getUser().getFirstName() + " " + principal.getUser().getLastName()));
    }

    private void authenticate(String email, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

}
