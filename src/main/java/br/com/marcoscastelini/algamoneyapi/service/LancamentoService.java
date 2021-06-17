package br.com.marcoscastelini.algamoneyapi.service;

import br.com.marcoscastelini.algamoneyapi.model.Lancamento;
import br.com.marcoscastelini.algamoneyapi.model.Pessoa;
import br.com.marcoscastelini.algamoneyapi.repository.LancamentoRepository;
import br.com.marcoscastelini.algamoneyapi.service.exception.PessoaInativaException;
import br.com.marcoscastelini.algamoneyapi.service.exception.PessoaNaoLocalizadaException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final PessoaService pessoaService;

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaService.buscarPorId(lancamento.getPessoa().getId())
                .orElseThrow(PessoaNaoLocalizadaException::new);

        if(pessoa.isInativa()) {
            throw new PessoaInativaException();
        }

        return lancamentoRepository.save(lancamento);
    }
}
