/*package com.hroutsourcuing.hroutsourcing.Security;

import com.hroutsourcuing.hroutsourcing.Models.modelRol;
import com.hroutsourcuing.hroutsourcing.Models.modelUsuario;
import com.hroutsourcuing.hroutsourcing.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
    private UsuariosRepository usuariosRepo;

    @Autowired
    public CustomUsersDetailsService(UsuariosRepository usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
    }

    //Método para traernos una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(List<modelRol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
    //Método para traernos un usuario con todos sus datos por medio de sus username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        modelUsuario usuarios = usuariosRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuarios.getEmail(), usuarios.getPassword(), mapToAuthorities(usuarios.getRoles()));
    }
}*/

package com.noticias.Security;

import com.noticias.Model.ModelUser;
import com.noticias.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuariosRepo;

    @Autowired
    public CustomUsersDetailsService(IUsuarioRepository usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
    }

    // Método para convertir roles a una lista de GrantedAuthority
    public Collection<GrantedAuthority> mapToAuthorities(ModelUser.Rol rol) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    // Cargar usuario por email y retornarlo como UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario en el repositorio
        ModelUser usuario = usuariosRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el username: " + username));

        // Retornar el usuario, asegurando que tiene GrantedAuthorities correctas
        return new User(usuario.getUsername(), usuario.getPassword(), mapToAuthorities(usuario.getRol()));
    }
}

