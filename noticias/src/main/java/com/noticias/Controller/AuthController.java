package com.noticias.Controller;

import com.noticias.Model.ModelUser;
import com.noticias.Security.JwtUtil;
import com.noticias.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping ("/api/auth/")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("register")
    public ResponseEntity<ModelUser> register(@RequestBody ModelUser user) {
        userService.registrarUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);//(Map.of("message", "Usuario registrado con exito."));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody ModelUser user) {
        // Autenticamos las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // Establecemos la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generamos el token con los detalles del usuario autenticado
        String token = jwtUtil.generarToken(authentication);

        // Devolvemos la respuesta con el token
        return ResponseEntity.ok(Map.of("token", token));
    }

}
