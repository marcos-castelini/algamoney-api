package br.com.marcoscastelini.algamoneyapi.repository;

import br.com.marcoscastelini.algamoneyapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
