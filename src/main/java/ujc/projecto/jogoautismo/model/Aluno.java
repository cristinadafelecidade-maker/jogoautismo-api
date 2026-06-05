package ujc.projecto.jogoautismo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String apelido;

    @Column(length = 20, unique = true)
    private String nuit;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_autismo", length = 20)
    private NivelAutismo nivelAutismo;

    @Column(name = "necessidade_especial", length = 255)
    private String necessidadeEspecial;

    @ManyToOne
    @JoinColumn(name = "encarregado_id", nullable = false)
    private Encarregado encarregado;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Progresso> progressos = new ArrayList<>();

    public Aluno() {}

    public Long getId() { return id; }
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
    public void setNecessidadeEspecial(String ne) { this.necessidadeEspecial = ne; }
    public Encarregado getEncarregado() { return encarregado; }
    public void setEncarregado(Encarregado encarregado) { this.encarregado = encarregado; }
    public List<Progresso> getProgressos() { return progressos; }
    public void setProgressos(List<Progresso> progressos) { this.progressos = progressos; }
}
/**
 * Entidade responsável por representar um aluno/criança
 * participante das actividades educativas.
 */
