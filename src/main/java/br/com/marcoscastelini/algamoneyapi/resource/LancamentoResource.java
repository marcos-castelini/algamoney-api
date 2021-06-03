package br.com.marcoscastelini.algamoneyapi.resource;

import br.com.marcoscastelini.algamoneyapi.event.RecursoCriadoEvent;
import br.com.marcoscastelini.algamoneyapi.model.Lancamento;
import br.com.marcoscastelini.algamoneyapi.repository.LancamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    private final LancamentoRepository repository;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<?> buscarLancamentos() {
        List<Lancamento> lancamentos = repository.findAll();
        if (lancamentos.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarLancamentoPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> inserirLancamento(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = repository.save(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }
}
