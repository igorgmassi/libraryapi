package io.com.igorgmassi.libraryapi.repository;

import io.com.igorgmassi.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
