package br.com.marcoscastelini.algamoneyapi.repository;

import br.com.marcoscastelini.algamoneyapi.model.Lancamento;
import br.com.marcoscastelini.algamoneyapi.repository.lancamento.LancamentoRespositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRespositoryQuery {
}
