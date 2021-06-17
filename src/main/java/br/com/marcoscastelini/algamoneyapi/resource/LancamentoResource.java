package br.com.marcoscastelini.algamoneyapi.resource;

import br.com.marcoscastelini.algamoneyapi.event.RecursoCriadoEvent;
import br.com.marcoscastelini.algamoneyapi.model.Lancamento;
import br.com.marcoscastelini.algamoneyapi.repository.LancamentoRepository;
import br.com.marcoscastelini.algamoneyapi.repository.filter.LancamentoFilter;
import br.com.marcoscastelini.algamoneyapi.service.LancamentoService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    private final LancamentoRepository repository;
    private final LancamentoService service;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<Page<?>> buscarLancamentos(LancamentoFilter filter, Pageable pageable) {
        return ResponseEntity.ok(repository.filtrar(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarLancamentoPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> inserirLancamento(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = service.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirLancamento(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
