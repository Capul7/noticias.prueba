package com.noticias.Service;

import com.noticias.Model.ModelUser;
import com.noticias.Repository.IUsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ModelUser registrarUser(ModelUser modelUser) {

        if (usuarioRepository.existsByUsername(modelUser.getUsername())) {
            System.err.println("El usuario ya existe en la base de datos: " + modelUser.getUsername());
            return null; //verificamos si el usuario existe y si es asi con el returnn null no lo guarda
        }
        modelUser.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        try {
            usuarioRepository.save(modelUser);
            System.out.println("Usuario registrado correctamente: " + modelUser.getUsername());
        } catch (Exception e) {
            System.err.println("Error al registrar el usuario: " + e.getMessage());
        }
        return modelUser;
    }


    public Optional<ModelUser> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return false;
    }
}

