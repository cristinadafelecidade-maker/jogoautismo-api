package ujc.projecto.jogoautismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.projecto.jogoautismo.model.Progresso;
import java.util.List;

public interface ProgressoRepository extends JpaRepository<Progresso, Long> {
    List<Progresso> findByAlunoId(Long alunoId);
    List<Progresso> findByAtividadeId(Long atividadeId);

    // Usado para garantir que o ENCARREGADO veja apenas o progresso dos seus alunos.
    List<Progresso> findByAlunoEncarregadoId(Long encarregadoId);
}
