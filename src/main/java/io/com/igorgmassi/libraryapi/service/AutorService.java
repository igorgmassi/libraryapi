package io.com.igorgmassi.libraryapi.service;

import io.com.igorgmassi.libraryapi.model.Autor;
import io.com.igorgmassi.libraryapi.repository.AutorRepository;
import io.com.igorgmassi.libraryapi.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorValidator autorValidator;

    public Autor salvar( Autor autor){
        autorValidator.validarAutor(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id ){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        autorRepository.delete(autor);
    }

    /*Now i just gonna make a sacrilege only for purpose to follow the class, but
    * i know that it is not the most clean and efficient way to implement*/
    public List<Autor> pesquisar(String nome, String nacionalidade){

        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);

        }

        if(nome != null){
            return autorRepository.findByNome(nome);
        }

        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();

        //That hurt me to see that
    }

    public void atualizar (Autor autor){

        if(autor.getId() == null){
            throw new IllegalArgumentException("Autor precisa estar cadastrado na base de dados");
        }
        autorValidator.validarAutor(autor);
        autorRepository.save(autor);
    }
}
