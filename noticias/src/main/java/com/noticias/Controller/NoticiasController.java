package com.noticias.Controller;

import com.noticias.Model.ModelNoticias;
import com.noticias.Model.ModelNoticiasRecomendadas;
import com.noticias.Repository.INoticiasRecomendadasRepository;
import com.noticias.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/noticias/")
public class NoticiasController {
    private final NoticiaService noticiaService;
    private Long id;

    @Autowired
    public NoticiasController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    //Endpoint para listar noticias le agregarmos un if para que nos traiga una lista con 5 noticias.
    @GetMapping(value = "listar", headers = "Accept=application/json")
    public ResponseEntity<List<ModelNoticias>> obtenerNoticias() {
        List<ModelNoticias> noticias = noticiaService.obtenerNoticias();
        if (noticias.size() > 5) {
            noticias = noticias.subList(0, 5);
        }
        return ResponseEntity.ok(noticias);
    }

    //Endpoint para listar por id
    @GetMapping(value = "listarPorId/{id}", headers = "Accept=application/json")
   public ResponseEntity<ModelNoticias> obtenerNoticiasPorId(@PathVariable Long id) {
        Optional<ModelNoticias> noticias = noticiaService.obtenerNoticiaPorId(id);
        return noticias.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //Endpoint para que nos devuelva noticcias recomendadas agregamos un if para que nos gener 3 por cada noticia.
    @GetMapping(value = "{id}/recomendadas")
    public ResponseEntity<List<ModelNoticias>> obtenerNoticiasRecomendadas(@PathVariable Long id) {
        Optional<ModelNoticias> noticias = noticiaService.obtenerNoticiaPorId(id);
        if (noticias.isPresent()) {
            List<ModelNoticiasRecomendadas> recomendaciones = INoticiasRecomendadasRepository.findByNoticia(noticias.get());
            List<ModelNoticias> noticiasRecomendadas = recomendaciones.stream()
                    .map(ModelNoticiasRecomendadas::getRecomendada)
                    .limit(3)
                    .toList();
            return ResponseEntity.ok(noticiasRecomendadas);
        }
        return ResponseEntity.notFound().build();
    }


    //Metodo para crear la noticia
    @PostMapping(value = "crear")
    public ModelNoticias guardarNoticia(@RequestBody ModelNoticias modelNoticias) {
        return noticiaService.guardarNoticia(modelNoticias);
    }
}

