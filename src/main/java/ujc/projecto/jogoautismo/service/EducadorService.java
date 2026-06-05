package ujc.projecto.jogoautismo.service;

import org.springframework.stereotype.Service;
import ujc.projecto.jogoautismo.model.Educador;
import ujc.projecto.jogoautismo.repository.EducadorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EducadorService {

    private final EducadorRepository educadorRepository;

    public EducadorService(EducadorRepository educadorRepository) {
        this.educadorRepository = educadorRepository;
    }

    public List<Educador> listarTodos() {
        return educadorRepository.findAll();
    }

    public Optional<Educador> buscarPorId(Long id) {
        return educadorRepository.findById(id);
    }

    public List<Educador> buscarPorNome(String nome) {
        return educadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Educador cadastrar(Educador educador) {
        return educadorRepository.save(educador);
    }

    public Educador actualizar(Long id, Educador dados) {
        Educador educador = educadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Educador não encontrado: " + id));
        educador.setNome(dados.getNome());
        educador.setEmail(dados.getEmail());
        educador.setCelular(dados.getCelular());
        educador.setEspecialidade(dados.getEspecialidade());
        return educadorRepository.save(educador);
    }

    public void remover(Long id) {
        if (!educadorRepository.existsById(id)) {
            throw new IllegalArgumentException("Educador não encontrado: " + id);
        }
        educadorRepository.deleteById(id);
    }
}
