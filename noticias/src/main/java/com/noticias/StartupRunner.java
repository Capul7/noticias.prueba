package com.noticias;
import com.noticias.Model.ModelUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.noticias.Security.JwtUtil;
import com.noticias.Service.UserService;

import java.util.Collections;

import static com.noticias.Model.ModelUser.Rol.Admin;

@Component
public class StartupRunner implements CommandLineRunner {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Usuario creado porr default
        String username = "admin";
        String password = "123456";

        // Verifica si el usuario existe, si no lo crea
        if (!userService.existsByUsername(username)) {
            ModelUser newUser = new ModelUser();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password)); // Guarda la contraseña encriptada
            newUser.setRol(ModelUser.Rol.Admin); // Asigna el rol ADMIN
            System.out.println(" Usuario no creado correctamente: " + username);
        } else {
            System.out.println(" El usuario ya existe: " + username);
        }


        // Autenticación simulada (No usa AuthenticationManager directamente)
        UserDetails userDetails = new User(username, password, Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Generar el token JWT
        String token = jwtUtil.generarToken(authentication);

        // Imprimir el token en consola
        System.out.println("Token generado al iniciar la aplicación:");
        System.out.println(token);
    }
}
