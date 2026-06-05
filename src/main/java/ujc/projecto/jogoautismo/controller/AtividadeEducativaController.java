package ujc.projecto.jogoautismo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.projecto.jogoautismo.model.AtividadeEducativa;
import ujc.projecto.jogoautismo.model.NivelDificuldade;
import ujc.projecto.jogoautismo.service.AtividadeEducativaService;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
@Tag(name = "Atividades Educativas", description = "Gestão de actividades educativas do jogo")
public class AtividadeEducativaController {

    private final AtividadeEducativaService atividadeService;

    public AtividadeEducativaController(AtividadeEducativaService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as atividades")
    public ResponseEntity<List<AtividadeEducativa>> listar() {
        return ResponseEntity.ok(atividadeService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atividade por ID")
    public ResponseEntity<AtividadeEducativa> buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nivel/{nivel}")
    @Operation(summary = "Filtrar atividades por nível de dificuldade",
               description = "Valores possíveis: FACIL, MEDIO, DIFICIL")
    public ResponseEntity<List<AtividadeEducativa>> buscarPorNivel(@PathVariable NivelDificuldade nivel) {
        return ResponseEntity.ok(atividadeService.buscarPorNivel(nivel));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Pesquisar atividade por título")
    public ResponseEntity<List<AtividadeEducativa>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(atividadeService.buscarPorTitulo(titulo));
    }

    @PostMapping
    @Operation(summary = "Registar nova atividade educativa",
               description = "Exemplo: { \"titulo\": \"Cores e Formas\", \"descricao\": \"...\", \"nivelDificuldade\": \"FACIL\", \"tempoEstimado\": 15 }")
    @ApiResponse(responseCode = "201", description = "Atividade criada com sucesso")
    public ResponseEntity<AtividadeEducativa> registar(@Valid @RequestBody AtividadeEducativa atividade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeService.registar(atividade));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar atividade educativa")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody AtividadeEducativa dados) {
        try {
            return ResponseEntity.ok(atividadeService.actualizar(id, dados));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover atividade educativa")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            atividadeService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
