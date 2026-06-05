package ujc.projecto.jogoautismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ujc.projecto.jogoautismo.model.Educador;
import java.util.List;
import java.util.Optional;

public interface EducadorRepository extends JpaRepository<Educador, Long> {
    List<Educador> findByNomeContainingIgnoreCase(String nome);
    Optional<Educador> findByEmail(String email);
}
