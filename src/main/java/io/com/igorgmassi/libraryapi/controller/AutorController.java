package io.com.igorgmassi.libraryapi.controller;

import io.com.igorgmassi.libraryapi.dto.AutorDTO;
import io.com.igorgmassi.libraryapi.model.Autor;
import io.com.igorgmassi.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<Void> cadastrarAutor(@RequestBody AutorDTO dto) {
        Autor autorEntity = dto.mapearParaAutor();
        autorService.salvar(autorEntity);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(autorEntity.getId()).
                toUri();

        return ResponseEntity.created(location).build();
    }
}
