package br.com.marcoscastelini.algamoneyapi.resource;

import br.com.marcoscastelini.algamoneyapi.model.Categoria;
import br.com.marcoscastelini.algamoneyapi.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final CategoriaRepository repository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody @Valid Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = repository.save(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(categoriaSalva.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categoria);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo){
        return repository.findById(codigo)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
