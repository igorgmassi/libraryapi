package io.com.igorgmassi.libraryapi.controller;

import io.com.igorgmassi.libraryapi.dto.AutorDTO;
import io.com.igorgmassi.libraryapi.model.Autor;
import io.com.igorgmassi.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> buscarAutorPorId(@PathVariable String id){
        try{
            UUID uuid = UUID.fromString(id);

            Optional<Autor> autorResponse = autorService.buscarPorId(uuid);
            if(autorResponse.isPresent()){
                Autor autor = autorResponse.get();
                AutorDTO dto = new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                );

                return ResponseEntity.ok(dto);
            }

            return ResponseEntity.notFound().build();

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
