package io.com.igorgmassi.libraryapi.service;

import io.com.igorgmassi.libraryapi.model.Autor;
import io.com.igorgmassi.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor salvar( Autor autor){
        return autorRepository.save(autor);

    }
}
