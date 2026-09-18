package com.inovagab.inovagab;

import com.inovagab.inovagab.model.Role;
import com.inovagab.inovagab.model.Usuario;
import com.inovagab.inovagab.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner inicializarUsuarios(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            criarUsuarioSeNaoExistir(
                    usuarioRepository,
                    passwordEncoder,
                    "Operador InovaGAB",
                    "operador@inovagab.com",
                    "123456",
                    Role.OPERADOR
            );

            criarUsuarioSeNaoExistir(
                    usuarioRepository,
                    passwordEncoder,
                    "Gestor InovaGAB",
                    "gestor@inovagab.com",
                    "123456",
                    Role.GESTOR
            );

            criarUsuarioSeNaoExistir(
                    usuarioRepository,
                    passwordEncoder,
                    "Líder InovaGAB",
                    "lider@inovagab.com",
                    "123456",
                    Role.LIDER
            );
        };
    }

    private void criarUsuarioSeNaoExistir(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            String nome,
            String email,
            String senha,
            Role role) {

        if (usuarioRepository.findByEmail(email).isEmpty()) {

            Usuario usuario = new Usuario(
                    nome,
                    email,
                    passwordEncoder.encode(senha),
                    role
            );

            usuarioRepository.save(usuario);

            System.out.println("Usuário criado: " + email);
        }
    }
}