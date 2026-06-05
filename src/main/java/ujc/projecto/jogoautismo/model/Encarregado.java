package ujc.projecto.jogoautismo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "encarregados")
public class Encarregado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @Email
    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20)
    private String celular;

    @Column(length = 50)
    private String parentesco;

    @OneToMany(mappedBy = "encarregado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Aluno> alunos = new ArrayList<>();

    public Encarregado() {}

    public Encarregado(String nome, String email, String celular, String parentesco) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.parentesco = parentesco;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }
    public List<Aluno> getAlunos() { return alunos; }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }
}
