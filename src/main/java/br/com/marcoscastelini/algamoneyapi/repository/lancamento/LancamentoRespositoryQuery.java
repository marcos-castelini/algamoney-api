package br.com.marcoscastelini.algamoneyapi.repository.lancamento;

import br.com.marcoscastelini.algamoneyapi.model.Lancamento;
import br.com.marcoscastelini.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRespositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
}
