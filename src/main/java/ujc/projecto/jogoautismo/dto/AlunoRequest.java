package ujc.projecto.jogoautismo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ujc.projecto.jogoautismo.model.Genero;
import ujc.projecto.jogoautismo.model.NivelAutismo;

public class AlunoRequest {

    @NotBlank
    private String nome;
    private String apelido;
    private String nuit;
    private Genero genero;
    private NivelAutismo nivelAutismo;
    private String necessidadeEspecial;

    @NotNull
    private Long encarregadoId;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
    public String getNuit() { return nuit; }
    public void setNuit(String nuit) { this.nuit = nuit; }
    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
    public NivelAutismo getNivelAutismo() { return nivelAutismo; }
    public void setNivelAutismo(NivelAutismo nivelAutismo) { this.nivelAutismo = nivelAutismo; }
    public String getNecessidadeEspecial() { return necessidadeEspecial; }
    public void setNecessidadeEspecial(String necessidadeEspecial) { this.necessidadeEspecial = necessidadeEspecial; }
    public Long getEncarregadoId() { return encarregadoId; }
    public void setEncarregadoId(Long encarregadoId) { this.encarregadoId = encarregadoId; }
}
