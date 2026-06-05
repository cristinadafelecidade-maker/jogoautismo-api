package ujc.projecto.jogoautismo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "educadores")
public class Educador {

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

    @Column(length = 100)
    private String especialidade;

    public Educador() {}

    public Educador(String nome, String email, String celular, String especialidade) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.especialidade = especialidade;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
