package ujc.projecto.jogoautismo.service;

import org.springframework.stereotype.Service;
import ujc.projecto.jogoautismo.dto.ProgressoRequest;
import ujc.projecto.jogoautismo.model.Aluno;
import ujc.projecto.jogoautismo.model.AtividadeEducativa;
import ujc.projecto.jogoautismo.model.Progresso;
import ujc.projecto.jogoautismo.repository.AlunoRepository;
import ujc.projecto.jogoautismo.repository.AtividadeEducativaRepository;
import ujc.projecto.jogoautismo.repository.ProgressoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final AlunoRepository alunoRepository;
    private final AtividadeEducativaRepository atividadeRepository;

    public ProgressoService(ProgressoRepository progressoRepository,
                             AlunoRepository alunoRepository,
                             AtividadeEducativaRepository atividadeRepository) {
        this.progressoRepository = progressoRepository;
        this.alunoRepository = alunoRepository;
        this.atividadeRepository = atividadeRepository;
    }

    public List<Progresso> listarTodos() {
        return progressoRepository.findAll();
    }

    public Optional<Progresso> buscarPorId(Long id) {
        return progressoRepository.findById(id);
    }

    public List<Progresso> buscarPorAluno(Long alunoId) {
        return progressoRepository.findByAlunoId(alunoId);
    }

    public List<Progresso> buscarPorAtividade(Long atividadeId) {
        return progressoRepository.findByAtividadeId(atividadeId);
    }

    public List<Progresso> buscarPorEncarregado(Long encarregadoId) {
        return progressoRepository.findByAlunoEncarregadoId(encarregadoId);
    }

    public Progresso registar(ProgressoRequest request) {
        Aluno aluno = alunoRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + request.getAlunoId()));

        AtividadeEducativa atividade = atividadeRepository.findById(request.getAtividadeId())
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada: " + request.getAtividadeId()));

        Progresso progresso = new Progresso();
        progresso.setAluno(aluno);
        progresso.setAtividade(atividade);
        progresso.setPontuacao(request.getPontuacao());
        progresso.setDataRealizacao(request.getDataRealizacao() != null
                ? request.getDataRealizacao() : LocalDate.now());

        return progressoRepository.save(progresso);
    }

    public Progresso actualizar(Long id, ProgressoRequest request) {
        Progresso progresso = progressoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progresso não encontrado: " + id));

        if (request.getPontuacao() != null) progresso.setPontuacao(request.getPontuacao());
        if (request.getDataRealizacao() != null) progresso.setDataRealizacao(request.getDataRealizacao());

        if (request.getAlunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.getAlunoId())
                    .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + request.getAlunoId()));
            progresso.setAluno(aluno);
        }
        if (request.getAtividadeId() != null) {
            AtividadeEducativa atividade = atividadeRepository.findById(request.getAtividadeId())
                    .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada: " + request.getAtividadeId()));
            progresso.setAtividade(atividade);
        }

        return progressoRepository.save(progresso);
    }

    public void remover(Long id) {
        if (!progressoRepository.existsById(id)) {
            throw new IllegalArgumentException("Progresso não encontrado: " + id);
        }
        progressoRepository.deleteById(id);
    }
}
