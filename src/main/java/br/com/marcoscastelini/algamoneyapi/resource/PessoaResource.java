package br.com.marcoscastelini.algamoneyapi.resource;

import br.com.marcoscastelini.algamoneyapi.model.Pessoa;
import br.com.marcoscastelini.algamoneyapi.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    private final PessoaRepository repository;

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody @Valid Pessoa pessoa){
        Pessoa pessoaSalva = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pessoaSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(pessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
