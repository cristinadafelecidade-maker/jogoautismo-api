package ujc.projecto.jogoautismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.projecto.jogoautismo.model.Encarregado;
import java.util.List;
import java.util.Optional;

public interface EncarregadoRepository extends JpaRepository<Encarregado, Long> {
    List<Encarregado> findByNomeContainingIgnoreCase(String nome);
    Optional<Encarregado> findByEmail(String email);
}
