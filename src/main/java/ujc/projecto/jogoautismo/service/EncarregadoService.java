package ujc.projecto.jogoautismo.service;

import org.springframework.stereotype.Service;
import ujc.projecto.jogoautismo.model.Encarregado;
import ujc.projecto.jogoautismo.repository.EncarregadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EncarregadoService {

    private final EncarregadoRepository encarregadoRepository;

    public EncarregadoService(EncarregadoRepository encarregadoRepository) {
        this.encarregadoRepository = encarregadoRepository;
    }

    public List<Encarregado> listarTodos() {
        return encarregadoRepository.findAll();
    }

    public Optional<Encarregado> buscarPorId(Long id) {
        return encarregadoRepository.findById(id);
    }

    public List<Encarregado> buscarPorNome(String nome) {
        return encarregadoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Encarregado cadastrar(Encarregado encarregado) {
        return encarregadoRepository.save(encarregado);
    }

    public Encarregado actualizar(Long id, Encarregado dados) {
        Encarregado encarregado = encarregadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Encarregado não encontrado: " + id));
        encarregado.setNome(dados.getNome());
        encarregado.setEmail(dados.getEmail());
        encarregado.setCelular(dados.getCelular());
        encarregado.setParentesco(dados.getParentesco());
        return encarregadoRepository.save(encarregado);
    }

    public void remover(Long id) {
        if (!encarregadoRepository.existsById(id)) {
            throw new IllegalArgumentException("Encarregado não encontrado: " + id);
        }
        encarregadoRepository.deleteById(id);
    }
}
