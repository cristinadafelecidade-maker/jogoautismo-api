package ujc.projecto.jogoautismo.service;

import org.springframework.stereotype.Service;
import ujc.projecto.jogoautismo.dto.AlunoRequest;
import ujc.projecto.jogoautismo.model.Aluno;
import ujc.projecto.jogoautismo.model.Encarregado;
import ujc.projecto.jogoautismo.repository.AlunoRepository;
import ujc.projecto.jogoautismo.repository.EncarregadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final EncarregadoRepository encarregadoRepository;

    public AlunoService(AlunoRepository alunoRepository, EncarregadoRepository encarregadoRepository) {
        this.alunoRepository = alunoRepository;
        this.encarregadoRepository = encarregadoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Aluno> buscarPorEncarregado(Long encarregadoId) {
        return alunoRepository.findByEncarregadoId(encarregadoId);
    }

    public Aluno registar(AlunoRequest request) {
        Encarregado encarregado = encarregadoRepository.findById(request.getEncarregadoId())
                .orElseThrow(() -> new IllegalArgumentException("Encarregado não encontrado: " + request.getEncarregadoId()));

        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setApelido(request.getApelido());
        aluno.setNuit(request.getNuit());
        aluno.setGenero(request.getGenero());
        aluno.setNivelAutismo(request.getNivelAutismo());
        aluno.setNecessidadeEspecial(request.getNecessidadeEspecial());
        aluno.setEncarregado(encarregado);

        return alunoRepository.save(aluno);
    }

    public Aluno actualizar(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));

        Encarregado encarregado = encarregadoRepository.findById(request.getEncarregadoId())
                .orElseThrow(() -> new IllegalArgumentException("Encarregado não encontrado: " + request.getEncarregadoId()));

        aluno.setNome(request.getNome());
        aluno.setApelido(request.getApelido());
        aluno.setNuit(request.getNuit());
        aluno.setGenero(request.getGenero());
        aluno.setNivelAutismo(request.getNivelAutismo());
        aluno.setNecessidadeEspecial(request.getNecessidadeEspecial());
        aluno.setEncarregado(encarregado);

        return alunoRepository.save(aluno);
    }

    public Aluno associarEncarregado(Long alunoId, Long encarregadoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + alunoId));
        Encarregado encarregado = encarregadoRepository.findById(encarregadoId)
                .orElseThrow(() -> new IllegalArgumentException("Encarregado não encontrado: " + encarregadoId));
        aluno.setEncarregado(encarregado);
        return alunoRepository.save(aluno);
    }

    public void remover(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new IllegalArgumentException("Aluno não encontrado: " + id);
        }
        alunoRepository.deleteById(id);
    }
}
