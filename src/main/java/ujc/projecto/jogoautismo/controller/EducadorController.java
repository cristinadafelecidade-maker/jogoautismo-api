package ujc.projecto.jogoautismo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.projecto.jogoautismo.model.Educador;
import ujc.projecto.jogoautismo.service.EducadorService;

import java.util.List;

@RestController
@RequestMapping("/api/educadores")
@Tag(name = "Educadores", description = "Gestão de educadores e especialistas")
public class EducadorController {

    private final EducadorService educadorService;

    public EducadorController(EducadorService educadorService) {
        this.educadorService = educadorService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os educadores")
    public ResponseEntity<List<Educador>> listar() {
        return ResponseEntity.ok(educadorService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar educador por ID")
    public ResponseEntity<Educador> buscarPorId(@PathVariable Long id) {
        return educadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Pesquisar educador por nome")
    public ResponseEntity<List<Educador>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(educadorService.buscarPorNome(nome));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo educador",
               description = "Exemplo: { \"nome\": \"Prof. João\", \"email\": \"joao@escola.mz\", \"celular\": \"841234567\", \"especialidade\": \"Psicologia Infantil\" }")
    @ApiResponse(responseCode = "201", description = "Educador criado")
    public ResponseEntity<Educador> cadastrar(@Valid @RequestBody Educador educador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(educadorService.cadastrar(educador));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar educador")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Educador dados) {
        try {
            return ResponseEntity.ok(educadorService.actualizar(id, dados));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover educador")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            educadorService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
