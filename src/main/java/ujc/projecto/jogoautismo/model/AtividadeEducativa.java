package ujc.projecto.jogoautismo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "atividades_educativas")
public class AtividadeEducativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_dificuldade", length = 20)
    private NivelDificuldade nivelDificuldade;

    @Column(name = "tempo_estimado")
    private Integer tempoEstimado;

    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Progresso> progressos = new ArrayList<>();

    public AtividadeEducativa() {}

    public AtividadeEducativa(String titulo, String descricao, NivelDificuldade nivelDificuldade, Integer tempoEstimado) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.nivelDificuldade = nivelDificuldade;
        this.tempoEstimado = tempoEstimado;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public NivelDificuldade getNivelDificuldade() { return nivelDificuldade; }
    public void setNivelDificuldade(NivelDificuldade nivelDificuldade) { this.nivelDificuldade = nivelDificuldade; }
    public Integer getTempoEstimado() { return tempoEstimado; }
    public void setTempoEstimado(Integer tempoEstimado) { this.tempoEstimado = tempoEstimado; }
    public List<Progresso> getProgressos() { return progressos; }
    public void setProgressos(List<Progresso> progressos) { this.progressos = progressos; }
}
