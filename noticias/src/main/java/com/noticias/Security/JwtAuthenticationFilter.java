package com.noticias.Security;

import com.noticias.Model.ModelUser;
import com.noticias.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUsersDetailsService customUsersDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    private String obtenerTokenDeSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            //Aca si se encuentra el token JWT, se devuelve una subcadena de "bearerToken" que comienza después de los primeros 7 caracteres hasta el final de la cadena
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    @Override                       //Solicitud entrante
    protected void doFilterInternal(HttpServletRequest request,
                                    //Respuesta saliente
                                    HttpServletResponse response,
                                    //Mecanismo para invocar el siguiente filtro en la siguiente cadena de filtros
                                    FilterChain filterChain) throws ServletException, IOException {
        //Obtenemos los datos del token mediante el método desarrollado arriba
        String token = obtenerTokenDeSolicitud(request);
        // Validamos la información del token
        if (StringUtils.hasText(token) && jwtUtil.validarToken(token)) {
            // Obtener el nombre de usuario desde el token
            String username = jwtUtil.obtenerUsernameDeJwt(token);

            // Cargar detalles del usuario
            UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);

            // Obtener los roles desde el UserDetails y convertirlos en una lista de strings
            List<String> userRoles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            // Convertimos el enum a string y comparamos
            if (userRoles.contains(ModelUser.Rol.User.name()) || userRoles.contains(ModelUser.Rol.Admin.name())) {
                // Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Agregar detalles de la autenticación (como IP o user-agent)
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //Permite que la solicitud continue hacia el siguiente filtro en la cadena de filtro.
        filterChain.doFilter(request, response);
    }
}
