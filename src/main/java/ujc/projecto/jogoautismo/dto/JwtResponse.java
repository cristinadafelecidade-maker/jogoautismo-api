package ujc.projecto.jogoautismo.dto;

public class JwtResponse {
    private String token;
    private String tipo = "Bearer";
    private String email;
    private String perfil;
    private String nome;
    private Long usuarioId;
    private Long educadorId;
    private Long encarregadoId;
    private Long alunoId;

    public JwtResponse(String token, String email, String perfil, String nome,
                       Long usuarioId, Long educadorId, Long encarregadoId, Long alunoId) {
        this.token = token;
        this.email = email;
        this.perfil = perfil;
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.educadorId = educadorId;
        this.encarregadoId = encarregadoId;
        this.alunoId = alunoId;
    }

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
    public String getEmail() { return email; }
    public String getPerfil() { return perfil; }
    public String getNome() { return nome; }
    public Long getUsuarioId() { return usuarioId; }
    public Long getEducadorId() { return educadorId; }
    public Long getEncarregadoId() { return encarregadoId; }
    public Long getAlunoId() { return alunoId; }
}
