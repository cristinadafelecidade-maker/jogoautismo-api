package ujc.projecto.jogoautismo.service;

import org.springframework.stereotype.Service;
import ujc.projecto.jogoautismo.model.AtividadeEducativa;
import ujc.projecto.jogoautismo.model.NivelDificuldade;
import ujc.projecto.jogoautismo.repository.AtividadeEducativaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeEducativaService {

    private final AtividadeEducativaRepository atividadeRepository;

    public AtividadeEducativaService(AtividadeEducativaRepository atividadeRepository) {
        this.atividadeRepository = atividadeRepository;
    }

    public List<AtividadeEducativa> listarTodas() {
        return atividadeRepository.findAll();
    }

    public Optional<AtividadeEducativa> buscarPorId(Long id) {
        return atividadeRepository.findById(id);
    }

    public List<AtividadeEducativa> buscarPorNivel(NivelDificuldade nivel) {
        return atividadeRepository.findByNivelDificuldade(nivel);
    }

    public List<AtividadeEducativa> buscarPorTitulo(String titulo) {
        return atividadeRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public AtividadeEducativa registar(AtividadeEducativa atividade) {
        return atividadeRepository.save(atividade);
    }

    public AtividadeEducativa actualizar(Long id, AtividadeEducativa dados) {
        AtividadeEducativa atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada: " + id));
        atividade.setTitulo(dados.getTitulo());
        atividade.setDescricao(dados.getDescricao());
        atividade.setNivelDificuldade(dados.getNivelDificuldade());
        atividade.setTempoEstimado(dados.getTempoEstimado());
        return atividadeRepository.save(atividade);
    }

    public void remover(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new IllegalArgumentException("Atividade não encontrada: " + id);
        }
        atividadeRepository.deleteById(id);
    }
}
