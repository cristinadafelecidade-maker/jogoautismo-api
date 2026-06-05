package ujc.projecto.jogoautismo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "progressos")
public class Progresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private AtividadeEducativa atividade;

    @Column(nullable = false)
    private Integer pontuacao;

    @Column(name = "data_realizacao")
    private LocalDate dataRealizacao;

    public Progresso() {}

    public Progresso(Aluno aluno, AtividadeEducativa atividade, Integer pontuacao, LocalDate dataRealizacao) {
        this.aluno = aluno;
        this.atividade = atividade;
        this.pontuacao = pontuacao;
        this.dataRealizacao = dataRealizacao;
    }

    public Long getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public AtividadeEducativa getAtividade() { return atividade; }
    public void setAtividade(AtividadeEducativa atividade) { this.atividade = atividade; }
    public Integer getPontuacao() { return pontuacao; }
    public void setPontuacao(Integer pontuacao) { this.pontuacao = pontuacao; }
    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public void setDataRealizacao(LocalDate dataRealizacao) { this.dataRealizacao = dataRealizacao; }
}
