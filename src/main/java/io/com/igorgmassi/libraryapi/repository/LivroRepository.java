package io.com.igorgmassi.libraryapi.repository;

import io.com.igorgmassi.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
