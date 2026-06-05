package ujc.projecto.jogoautismo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ujc.projecto.jogoautismo.model.*;
import ujc.projecto.jogoautismo.repository.*;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final EducadorRepository educadorRepository;
    private final EncarregadoRepository encarregadoRepository;
    private final AlunoRepository alunoRepository;
    private final AtividadeEducativaRepository atividadeRepository;
    private final ProgressoRepository progressoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           EducadorRepository educadorRepository,
                           EncarregadoRepository encarregadoRepository,
                           AlunoRepository alunoRepository,
                           AtividadeEducativaRepository atividadeRepository,
                           ProgressoRepository progressoRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.educadorRepository = educadorRepository;
        this.encarregadoRepository = encarregadoRepository;
        this.alunoRepository = alunoRepository;
        this.atividadeRepository = atividadeRepository;
        this.progressoRepository = progressoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Só inicializa se não existirem dados
        if (usuarioRepository.existsByEmail("admin@jogoautismo.mz")) {
            System.out.println(">>> Dados já inicializados. A ignorar seed...");
            return;
        }

        System.out.println(">>> A inicializar dados de teste...");

        String senhaPadrao = passwordEncoder.encode("admin123");

        // ─── ADMIN ───
        Usuario admin = new Usuario();
        admin.setNome("Administrador do Sistema");
        admin.setEmail("admin@jogoautismo.mz");
        admin.setSenha(senhaPadrao);
        admin.setPerfil(Perfil.ADMIN);
        admin.setAtivo(true);
        usuarioRepository.save(admin);

        // ─── EDUCADORES ───
        Educador fatima = new Educador("Dra. Fátima Machava", "fatima@escola.mz", "841111111", "Educação Especial");
        Educador carlos = new Educador("Prof. Carlos Mondlane", "carlos@escola.mz", "842222222", "Psicopedagogia");
        Educador ana = new Educador("Dra. Ana Cossa", "ana.cossa@escola.mz", "843333333", "Terapia Ocupacional");
        educadorRepository.save(fatima);
        educadorRepository.save(carlos);
        educadorRepository.save(ana);

        Usuario uFatima = new Usuario();
        uFatima.setNome("Dra. Fátima Machava");
        uFatima.setEmail("fatima@escola.mz");
        uFatima.setSenha(senhaPadrao);
        uFatima.setPerfil(Perfil.EDUCADOR);
        uFatima.setAtivo(true);
        uFatima.setEducador(fatima);
        usuarioRepository.save(uFatima);

        Usuario uCarlos = new Usuario();
        uCarlos.setNome("Prof. Carlos Mondlane");
        uCarlos.setEmail("carlos@escola.mz");
        uCarlos.setSenha(senhaPadrao);
        uCarlos.setPerfil(Perfil.EDUCADOR);
        uCarlos.setAtivo(true);
        uCarlos.setEducador(carlos);
        usuarioRepository.save(uCarlos);

        // ─── ENCARREGADOS ───
        Encarregado maria = new Encarregado("Maria João Sitoe", "maria@gmail.com", "844444444", "Mãe");
        Encarregado carlosEnc = new Encarregado("Carlos Pedro Muchanga", "carlosm@gmail.com", "845555555", "Pai");
        Encarregado julia = new Encarregado("Júlia Tembe", "julia@gmail.com", "846666666", "Avó");
        encarregadoRepository.save(maria);
        encarregadoRepository.save(carlosEnc);
        encarregadoRepository.save(julia);

        Usuario uMaria = new Usuario();
        uMaria.setNome("Maria João");
        uMaria.setEmail("maria@gmail.com");
        uMaria.setSenha(senhaPadrao);
        uMaria.setPerfil(Perfil.ENCARREGADO);
        uMaria.setAtivo(true);
        uMaria.setEncarregado(maria);
        usuarioRepository.save(uMaria);

        Usuario uCarlosEnc = new Usuario();
        uCarlosEnc.setNome("Carlos Muchanga");
        uCarlosEnc.setEmail("carlosm@gmail.com");
        uCarlosEnc.setSenha(senhaPadrao);
        uCarlosEnc.setPerfil(Perfil.ENCARREGADO);
        uCarlosEnc.setAtivo(true);
        uCarlosEnc.setEncarregado(carlosEnc);
        usuarioRepository.save(uCarlosEnc);

        // ─── ALUNOS ───
        Aluno nilton = new Aluno();
        nilton.setNome("Nilton");
        nilton.setApelido("Sitoe");
        nilton.setNuit("123456789");
        nilton.setGenero(Genero.MASCULINO);
        nilton.setNivelAutismo(NivelAutismo.MEDIO);
        nilton.setNecessidadeEspecial("Dificuldade de comunicação verbal");
        nilton.setEncarregado(maria);
        alunoRepository.save(nilton);

        Aluno anaAluna = new Aluno();
        anaAluna.setNome("Ana");
        anaAluna.setApelido("Muchanga");
        anaAluna.setNuit("987654321");
        anaAluna.setGenero(Genero.FEMININO);
        anaAluna.setNivelAutismo(NivelAutismo.LEVE);
        anaAluna.setNecessidadeEspecial("Hipersensibilidade sensorial");
        anaAluna.setEncarregado(carlosEnc);
        alunoRepository.save(anaAluna);

        Aluno pedro = new Aluno();
        pedro.setNome("Pedro");
        pedro.setApelido("Tembe");
        pedro.setNuit("456789123");
        pedro.setGenero(Genero.MASCULINO);
        pedro.setNivelAutismo(NivelAutismo.SEVERO);
        pedro.setNecessidadeEspecial("Dificuldade de interação social");
        pedro.setEncarregado(julia);
        alunoRepository.save(pedro);

        Aluno sofia = new Aluno();
        sofia.setNome("Sofia");
        sofia.setApelido("Bila");
        sofia.setNuit("321654987");
        sofia.setGenero(Genero.FEMININO);
        sofia.setNivelAutismo(NivelAutismo.LEVE);
        sofia.setNecessidadeEspecial("Dificuldades de aprendizagem");
        sofia.setEncarregado(maria);
        alunoRepository.save(sofia);

        // Utilizadores para alunos
        Usuario uNilton = new Usuario();
        uNilton.setNome("Nilton Sitoe");
        uNilton.setEmail("nilton@aluno.mz");
        uNilton.setSenha(senhaPadrao);
        uNilton.setPerfil(Perfil.ALUNO);
        uNilton.setAtivo(true);
        uNilton.setAluno(nilton);
        usuarioRepository.save(uNilton);

        Usuario uAna = new Usuario();
        uAna.setNome("Ana Muchanga");
        uAna.setEmail("ana.m@aluno.mz");
        uAna.setSenha(senhaPadrao);
        uAna.setPerfil(Perfil.ALUNO);
        uAna.setAtivo(true);
        uAna.setAluno(anaAluna);
        usuarioRepository.save(uAna);

        // ─── ATIVIDADES EDUCATIVAS ───
        AtividadeEducativa a1 = new AtividadeEducativa("Cores e Formas",
                "Associar cores e formas básicas através de imagens coloridas", NivelDificuldade.FACIL, 15);
        AtividadeEducativa a2 = new AtividadeEducativa("Jogo da Memória Visual",
                "Encontrar pares iguais de imagens para treinar a memória", NivelDificuldade.FACIL, 20);
        AtividadeEducativa a3 = new AtividadeEducativa("Sons e Objetos",
                "Relacionar sons a objetos do quotidiano", NivelDificuldade.MEDIO, 25);
        AtividadeEducativa a4 = new AtividadeEducativa("Sequência de Números",
                "Ordenar números de 1 a 10 de forma crescente", NivelDificuldade.MEDIO, 30);
        AtividadeEducativa a5 = new AtividadeEducativa("Reconhecimento de Emoções",
                "Identificar emoções em expressões faciais", NivelDificuldade.DIFICIL, 35);
        AtividadeEducativa a6 = new AtividadeEducativa("Completar Palavras",
                "Completar palavras simples com as letras correctas", NivelDificuldade.DIFICIL, 40);
        atividadeRepository.save(a1);
        atividadeRepository.save(a2);
        atividadeRepository.save(a3);
        atividadeRepository.save(a4);
        atividadeRepository.save(a5);
        atividadeRepository.save(a6);

        // ─── PROGRESSOS ───
        progressoRepository.save(new Progresso(nilton, a1, 85, LocalDate.of(2026, 5, 4)));
        progressoRepository.save(new Progresso(nilton, a2, 90, LocalDate.of(2026, 5, 5)));
        progressoRepository.save(new Progresso(anaAluna, a1, 75, LocalDate.of(2026, 5, 4)));
        progressoRepository.save(new Progresso(anaAluna, a3, 80, LocalDate.of(2026, 5, 6)));
        progressoRepository.save(new Progresso(pedro, a1, 60, LocalDate.of(2026, 5, 4)));
        progressoRepository.save(new Progresso(sofia, a2, 95, LocalDate.of(2026, 5, 5)));

        System.out.println(">>> Dados de teste inicializados com sucesso!");
        System.out.println(">>> ADMIN: admin@jogoautismo.mz / admin123");
        System.out.println(">>> EDUCADOR: fatima@escola.mz / admin123");
        System.out.println(">>> ENCARREGADO: maria@gmail.com / admin123");
        System.out.println(">>> ALUNO: nilton@aluno.mz / admin123");
        System.out.println(">>> Swagger UI: http://localhost:8080/swagger-ui.html");
    }
}
