package ujc.projecto.jogoautismo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ujc.projecto.jogoautismo.dto.AlunoRequest;
import ujc.projecto.jogoautismo.model.Aluno;
import ujc.projecto.jogoautismo.service.AlunoService;
import ujc.projecto.jogoautismo.model.Usuario;
import ujc.projecto.jogoautismo.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos", description = "Gestão de alunos com autismo e necessidades especiais")
public class AlunoController {

    private final AlunoService alunoService;
    private final UsuarioRepository usuarioRepository;

    public AlunoController(AlunoService alunoService, UsuarioRepository usuarioRepository) {
        this.alunoService = alunoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos")
    @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso")
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }


    @GetMapping("/me")
    @Operation(summary = "Listar alunos associados ao encarregado logado",
               description = "Se o utilizador for ENCARREGADO, retorna somente os alunos/crianças associados a esse encarregado.")
    public ResponseEntity<?> meusAlunos(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();

        if (usuario.getEncarregado() == null) {
            return ResponseEntity.badRequest().body("Este utilizador não está associado a nenhum encarregado.");
        }

        return ResponseEntity.ok(alunoService.buscarPorEncarregado(usuario.getEncarregado().getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar aluno por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Consultar aluno por nome")
    public ResponseEntity<List<Aluno>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(alunoService.buscarPorNome(nome));
    }

    @GetMapping("/encarregado/{encarregadoId}")
    @Operation(summary = "Listar alunos por encarregado")
    public ResponseEntity<List<Aluno>> buscarPorEncarregado(@PathVariable Long encarregadoId) {
        return ResponseEntity.ok(alunoService.buscarPorEncarregado(encarregadoId));
    }

    @PostMapping
    @Operation(summary = "Registar novo aluno")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Aluno registado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou encarregado não encontrado")
    })
    public ResponseEntity<?> registar(@Valid @RequestBody AlunoRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.registar(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar dados do aluno")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Aluno actualizado"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody AlunoRequest request) {
        try {
            return ResponseEntity.ok(alunoService.actualizar(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/encarregado/{encarregadoId}")
    @Operation(summary = "Associar encarregado ao aluno")
    public ResponseEntity<?> associarEncarregado(@PathVariable Long id, @PathVariable Long encarregadoId) {
        try {
            return ResponseEntity.ok(alunoService.associarEncarregado(id, encarregadoId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover aluno")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            alunoService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
