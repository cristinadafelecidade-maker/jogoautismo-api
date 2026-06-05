package ujc.projecto.jogoautismo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.projecto.jogoautismo.dto.ProgressoRequest;
import ujc.projecto.jogoautismo.model.Progresso;
import ujc.projecto.jogoautismo.service.ProgressoService;
import ujc.projecto.jogoautismo.model.Usuario;
import ujc.projecto.jogoautismo.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/progressos")
@Tag(name = "Progressos", description = "Registo e consulta de progresso/pontuação dos alunos")
public class ProgressoController {

    private final ProgressoService progressoService;
    private final UsuarioRepository usuarioRepository;

    public ProgressoController(ProgressoService progressoService, UsuarioRepository usuarioRepository) {
        this.progressoService = progressoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos os registos de progresso")
    public ResponseEntity<List<Progresso>> listar() {
        return ResponseEntity.ok(progressoService.listarTodos());
    }


    @GetMapping("/me")
    @Operation(summary = "Consultar progresso dos alunos do encarregado logado",
               description = "Para ENCARREGADO, retorna apenas o progresso dos alunos associados ao seu encarregado_id. Para ALUNO, retorna apenas o próprio progresso.")
    public ResponseEntity<?> meusProgressos(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();

        if (usuario.getEncarregado() != null) {
            return ResponseEntity.ok(progressoService.buscarPorEncarregado(usuario.getEncarregado().getId()));
        }

        if (usuario.getAluno() != null) {
            return ResponseEntity.ok(progressoService.buscarPorAluno(usuario.getAluno().getId()));
        }

        return ResponseEntity.ok(progressoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar progresso por ID")
    public ResponseEntity<Progresso> buscarPorId(@PathVariable Long id) {
        return progressoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Listar progressos de um aluno")
    public ResponseEntity<List<Progresso>> buscarPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(progressoService.buscarPorAluno(alunoId));
    }

    @GetMapping("/atividade/{atividadeId}")
    @Operation(summary = "Listar progressos por atividade")
    public ResponseEntity<List<Progresso>> buscarPorAtividade(@PathVariable Long atividadeId) {
        return ResponseEntity.ok(progressoService.buscarPorAtividade(atividadeId));
    }

    @PostMapping
    @Operation(summary = "Registar progresso de um aluno numa atividade",
               description = "Exemplo: { \"alunoId\": 1, \"atividadeId\": 1, \"pontuacao\": 85, \"dataRealizacao\": \"2026-06-04\" }")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Progresso registado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Aluno ou atividade não encontrados")
    })
    public ResponseEntity<?> registar(@Valid @RequestBody ProgressoRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(progressoService.registar(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar progresso do aluno")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ProgressoRequest request) {
        try {
            return ResponseEntity.ok(progressoService.actualizar(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover registo de progresso")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            progressoService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
