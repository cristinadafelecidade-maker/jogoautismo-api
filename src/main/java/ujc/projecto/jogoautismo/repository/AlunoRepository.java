package ujc.projecto.jogoautismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.projecto.jogoautismo.model.Aluno;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
    List<Aluno> findByEncarregadoId(Long encarregadoId);
}
