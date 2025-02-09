package com.noticias.Repository;

import com.noticias.Model.ModelNoticias;
import com.noticias.Model.ModelNoticiasRecomendadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INoticiasRepository extends JpaRepository<ModelNoticias, Long> {
}
