package com.noticias.Service;

import com.noticias.Model.ModelNoticias;
import com.noticias.Repository.INoticiasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {
    private final INoticiasRepository noticiasRepository;

    @Autowired
    public NoticiaService(INoticiasRepository noticiasRepository) {
        this.noticiasRepository = noticiasRepository;
    }
    //metodo para generar todas las noticias
    public List<ModelNoticias> obtenerNoticias() {
        return noticiasRepository.findAll();
    }

    //Metodo para buscar por ID
    public Optional<ModelNoticias> obtenerNoticiaPorId(Long id) {
        return noticiasRepository.findById(id);
    }
    public ModelNoticias guardarNoticia(ModelNoticias modelNoticias) {
        return noticiasRepository.save(modelNoticias);
    }

    public void actualizarNoticia(ModelNoticias modelNoticias) {
        noticiasRepository.save(modelNoticias);
    }
}
