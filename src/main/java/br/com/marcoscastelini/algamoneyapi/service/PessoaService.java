package br.com.marcoscastelini.algamoneyapi.service;

import br.com.marcoscastelini.algamoneyapi.model.Pessoa;
import br.com.marcoscastelini.algamoneyapi.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository repository;

    public Optional<Pessoa> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
