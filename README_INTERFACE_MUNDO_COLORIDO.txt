INTERFACE MUNDO COLORIDO INTEGRADA

Este pacote usa a interface enviada pelo grupo como tela principal do jogo.

Como abrir:
1. mvn spring-boot:run
2. http://localhost:8080/index.html
   ou http://localhost:8080/jogos.html

A interface mantém o visual original Mundo Colorido e foi adaptada para:
- carregar crianças/alunos da API /api/alunos;
- criar/editar/remover perfis usando MySQL;
- usar login JWT automático com admin@jogoautismo.mz / admin123;
- gravar pontuação em /api/progressos quando a criança sai do jogo.

Swagger continua em:
http://localhost:8080/swagger-ui/index.html
