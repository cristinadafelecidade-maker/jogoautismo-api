package ujc.projecto.jogoautismo.dto;

import ujc.projecto.jogoautismo.model.Usuario;

public class UsuarioAtualResponse {
    private Long usuarioId;
    private String nome;
    private String email;
    private String perfil;
    private Long educadorId;
    private Long encarregadoId;
    private Long alunoId;

    public UsuarioAtualResponse(Usuario usuario) {
        this.usuarioId = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil().name();
        this.educadorId = usuario.getEducador() != null ? usuario.getEducador().getId() : null;
        this.encarregadoId = usuario.getEncarregado() != null ? usuario.getEncarregado().getId() : null;
        this.alunoId = usuario.getAluno() != null ? usuario.getAluno().getId() : null;
    }

    public Long getUsuarioId() { return usuarioId; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPerfil() { return perfil; }
    public Long getEducadorId() { return educadorId; }
    public Long getEncarregadoId() { return encarregadoId; }
    public Long getAlunoId() { return alunoId; }
}
