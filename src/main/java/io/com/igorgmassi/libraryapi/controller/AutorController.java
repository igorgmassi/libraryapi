package io.com.igorgmassi.libraryapi.controller;

import io.com.igorgmassi.libraryapi.dto.AutorDTO;
import io.com.igorgmassi.libraryapi.dto.ErroReposta;
import io.com.igorgmassi.libraryapi.exeptions.OperacaoNaoPermitidaException;
import io.com.igorgmassi.libraryapi.exeptions.RegistroDuplicadoException;
import io.com.igorgmassi.libraryapi.model.Autor;
import io.com.igorgmassi.libraryapi.service.AutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<Object> cadastrarAutor(@RequestBody AutorDTO dto) {
        try{
            Autor autorEntity = dto.mapearParaAutor();
            autorService.salvar(autorEntity);
            URI location = ServletUriComponentsBuilder.
                    fromCurrentRequest().
                    path("/{id}").
                    buildAndExpand(autorEntity.getId()).
                    toUri();

            return ResponseEntity.created(location).build();


        }catch(RegistroDuplicadoException e){
            ErroReposta erro = ErroReposta.repostaConflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable String id){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Autor> autorResponse = autorService.buscarPorId(uuid);

            if(autorResponse.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            autorService.deletar(autorResponse.get());
            return ResponseEntity.noContent().build();

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (OperacaoNaoPermitidaException e){

            ErroReposta erro = ErroReposta.repostaConflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisarAutores
            (@RequestParam(value = "nome", required = false) String nome,
             @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisar(nome, nacionalidade);

        List<AutorDTO> autoresDTO = resultado.stream().map(
                autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()))
                .toList();


        return  ResponseEntity.ok(autoresDTO);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarAutor(
            @PathVariable String id,
            @RequestBody AutorDTO dto){

        try{
            UUID uuid = UUID.fromString(id);
            Optional<Autor> autorResponse = autorService.buscarPorId(uuid);

            if(autorResponse.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            Autor autor = autorResponse.get();
            autor.setNome(dto.nome());
            autor.setDataNascimento(dto.dataNascimento());
            autor.setNacionalidade(dto  .nacionalidade());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();

        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().build();
        }catch(RegistroDuplicadoException e){

            ErroReposta erro = ErroReposta.repostaConflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }
}
