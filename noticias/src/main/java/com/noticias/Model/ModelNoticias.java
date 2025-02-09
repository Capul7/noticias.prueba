package com.noticias.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "noticias_tb")
public class ModelNoticias {
    @Id
    @Column (name = "id_noticia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idNoticia;
    String tituloNoticia;
    String imageNoticia;
    String descripcionNoticia;

    @Column( columnDefinition = "TEXT")
    private String contenido;

    private LocalDateTime fechaNoticia = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "noticias_recomendadas", joinColumns = @JoinColumn(name = "noticia_id"),
            inverseJoinColumns= @JoinColumn(name = "recomendada_id")
    )

    private List<ModelNoticias> recomendaciones;
}
