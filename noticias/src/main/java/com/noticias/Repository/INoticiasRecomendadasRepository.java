package com.noticias.Repository;

import com.noticias.Model.ModelNoticias;
import com.noticias.Model.ModelNoticiasRecomendadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INoticiasRecomendadasRepository extends JpaRepository<ModelNoticiasRecomendadas, Long>{
    @Query("SELECT r FROM ModelNoticiasRecomendadas r WHERE r.noticia = :noticia")
    static List<ModelNoticiasRecomendadas> findByNoticia(@Param("noticia") ModelNoticias noticia) {
        return null;
    }
    //List<ModelNoticiasRecomendadas> findByNoticia(ModelNoticias noticia);
}
