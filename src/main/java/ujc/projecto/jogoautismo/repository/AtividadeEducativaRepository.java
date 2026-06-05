package ujc.projecto.jogoautismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.projecto.jogoautismo.model.AtividadeEducativa;
import ujc.projecto.jogoautismo.model.NivelDificuldade;
import java.util.List;

public interface AtividadeEducativaRepository extends JpaRepository<AtividadeEducativa, Long> {
    List<AtividadeEducativa> findByTituloContainingIgnoreCase(String titulo);
    List<AtividadeEducativa> findByNivelDificuldade(NivelDificuldade nivel);
}
