package com.noticias.Repository;

import com.noticias.Model.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<ModelUser , Long> {
    boolean existsByUsername(String username);
    Optional<ModelUser> findByUsername(String username);
}
