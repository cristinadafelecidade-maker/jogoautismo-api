package ujc.projecto.jogoautismo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ujc.projecto.jogoautismo.dto.JwtResponse;
import ujc.projecto.jogoautismo.dto.UsuarioAtualResponse;
import ujc.projecto.jogoautismo.dto.LoginRequest;
import ujc.projecto.jogoautismo.model.Usuario;
import ujc.projecto.jogoautismo.repository.UsuarioRepository;
import ujc.projecto.jogoautismo.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Login e geração de token JWT")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica um utilizador e retorna um token JWT. " +
            "Credenciais de teste - ADMIN: admin@jogoautismo.mz / admin123 | " +
            "EDUCADOR: fatima@escola.mz / admin123 | " +
            "ENCARREGADO: maria@gmail.com / admin123 | " +
            "ALUNO: nilton@aluno.mz / admin123")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

        String token = jwtUtil.generateToken(authentication);

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(24 * 60 * 60)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new JwtResponse(
                        token,
                        usuario.getEmail(),
                        usuario.getPerfil().name(),
                        usuario.getNome(),
                        usuario.getId(),
                        usuario.getEducador() != null ? usuario.getEducador().getId() : null,
                        usuario.getEncarregado() != null ? usuario.getEncarregado().getId() : null,
                        usuario.getAluno() != null ? usuario.getAluno().getId() : null
                ));
    }


    @GetMapping("/me")
    @Operation(summary = "Utilizador autenticado", description = "Retorna o utilizador logado e os IDs associados ao perfil, como encarregadoId e alunoId.")
    public ResponseEntity<UsuarioAtualResponse> me(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(new UsuarioAtualResponse(usuario));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Remove o cookie JWT usado para proteger as páginas HTML.")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
