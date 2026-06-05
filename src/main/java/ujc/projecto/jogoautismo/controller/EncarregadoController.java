package ujc.projecto.jogoautismo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.projecto.jogoautismo.model.Encarregado;
import ujc.projecto.jogoautismo.service.EncarregadoService;

import java.util.List;

@RestController
@RequestMapping("/api/encarregados")
@Tag(name = "Encarregados", description = "Gestão de encarregados/responsáveis dos alunos")
public class EncarregadoController {

    private final EncarregadoService encarregadoService;

    public EncarregadoController(EncarregadoService encarregadoService) {
        this.encarregadoService = encarregadoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os encarregados")
    public ResponseEntity<List<Encarregado>> listar() {
        return ResponseEntity.ok(encarregadoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar encarregado por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Encarregado encontrado"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<Encarregado> buscarPorId(@PathVariable Long id) {
        return encarregadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Pesquisar encarregado por nome")
    public ResponseEntity<List<Encarregado>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(encarregadoService.buscarPorNome(nome));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo encarregado",
               description = "Exemplo: { \"nome\": \"Maria Sitoe\", \"email\": \"maria@gmail.com\", \"celular\": \"841234567\", \"parentesco\": \"Mãe\" }")
    @ApiResponse(responseCode = "201", description = "Encarregado criado")
    public ResponseEntity<Encarregado> cadastrar(@Valid @RequestBody Encarregado encarregado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(encarregadoService.cadastrar(encarregado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar encarregado")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Encarregado dados) {
        try {
            return ResponseEntity.ok(encarregadoService.actualizar(id, dados));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover encarregado")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Removido"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            encarregadoService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
