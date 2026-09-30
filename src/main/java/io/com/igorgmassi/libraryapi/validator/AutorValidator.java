package io.com.igorgmassi.libraryapi.validator;

import io.com.igorgmassi.libraryapi.exeptions.RegistroDuplicadoException;
import io.com.igorgmassi.libraryapi.model.Autor;
import io.com.igorgmassi.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    @Autowired
    private AutorRepository autorRepository;

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Já existe um Autor cadastrado com este registro");
        }

    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorOptional = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        if(autor.getId() == null){
            return autorOptional.isPresent();
        }
        return autorOptional.isPresent() && !autor.getId().equals(autorOptional.get().getId());
    }
    }

