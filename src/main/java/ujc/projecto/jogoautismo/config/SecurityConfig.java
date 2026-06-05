package ujc.projecto.jogoautismo.config;

import org.springframework.context.annotation.Bean;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import ujc.projecto.jogoautismo.security.JwtAuthFilter;
import ujc.projecto.jogoautismo.security.JwtUtil;
import ujc.projecto.jogoautismo.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Apenas ficheiros de apoio são ignorados pelo Spring Security.
        // As páginas HTML ficam protegidas no filterChain por perfil.
        return web -> web.ignoring().requestMatchers(
                "/css/**",
                "/js/**",
                "/img/**",
                "/favicon.ico"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Páginas e endpoints públicos
                .requestMatchers("/", "/index.html", "/login.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/logout").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                // Páginas protegidas por perfil
                .requestMatchers("/dashboard-admin.html").hasRole("ADMIN")
                .requestMatchers("/jogos.html").hasAnyRole("ALUNO", "ADMIN")
                .requestMatchers("/atividades.html").hasAnyRole("EDUCADOR", "ADMIN")
                .requestMatchers("/sessoes.html", "/sessoes.htm").hasAnyRole("ENCARREGADO", "EDUCADOR", "ADMIN")
                .requestMatchers("/criancas.html").hasAnyRole("EDUCADOR", "ADMIN")
                .requestMatchers("/responsaveis.html").hasRole("ADMIN")
                .requestMatchers("/educadores.html").hasRole("ADMIN")

                // ADMIN: acesso total
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Alunos: ADMIN pode tudo; EDUCADOR pode listar/ver; ENCARREGADO pode ver seus alunos
                .requestMatchers(HttpMethod.POST, "/api/alunos/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/alunos/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/alunos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/alunos/**").hasAnyRole("ADMIN", "EDUCADOR", "ENCARREGADO")

                // Educadores: ADMIN gere; EDUCADOR pode ver
                .requestMatchers(HttpMethod.POST, "/api/educadores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/educadores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/educadores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/educadores/**").hasAnyRole("ADMIN", "EDUCADOR")

                // Encarregados: ADMIN gere; ENCARREGADO pode ver
                .requestMatchers(HttpMethod.POST, "/api/encarregados/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/encarregados/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/encarregados/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/encarregados/**").hasAnyRole("ADMIN", "EDUCADOR", "ENCARREGADO")

                // Atividades: EDUCADOR e ADMIN gerem; ALUNO pode ver
                .requestMatchers(HttpMethod.POST, "/api/atividades/**").hasAnyRole("ADMIN", "EDUCADOR")
                .requestMatchers(HttpMethod.PUT, "/api/atividades/**").hasAnyRole("ADMIN", "EDUCADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/atividades/**").hasAnyRole("ADMIN", "EDUCADOR")
                .requestMatchers(HttpMethod.GET, "/api/atividades/**").hasAnyRole("ADMIN", "EDUCADOR", "ALUNO", "ENCARREGADO")

                // Progresso: EDUCADOR e ALUNO registam; ENCARREGADO consulta
                .requestMatchers(HttpMethod.POST, "/api/progressos/**").hasAnyRole("ADMIN", "EDUCADOR", "ALUNO")
                .requestMatchers(HttpMethod.PUT, "/api/progressos/**").hasAnyRole("ADMIN", "EDUCADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/progressos/**").hasAnyRole("ADMIN", "EDUCADOR")
                .requestMatchers(HttpMethod.GET, "/api/progressos/**").hasAnyRole("ADMIN", "EDUCADOR", "ENCARREGADO", "ALUNO")

                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    if (request.getRequestURI().startsWith("/api/")) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autenticado");
                    } else {
                        response.sendRedirect("/login.html");
                    }
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    if (request.getRequestURI().startsWith("/api/")) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sem permissão");
                    } else {
                        response.sendRedirect("/login.html");
                    }
                })
            )
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
