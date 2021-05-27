package br.com.marcoscastelini.algamoneyapi.repository;

import br.com.marcoscastelini.algamoneyapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
