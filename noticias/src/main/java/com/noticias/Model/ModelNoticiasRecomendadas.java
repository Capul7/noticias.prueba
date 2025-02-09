package com.noticias.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "noticias_recomendadas")
public class ModelNoticiasRecomendadas {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "noticia_id", nullable = false)
    private ModelNoticias noticia;

    @ManyToOne
    @JoinColumn(name = "recomendad_id", nullable = false)
    private ModelNoticias recomendada;
}
