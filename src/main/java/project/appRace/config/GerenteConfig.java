package project.appRace.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;
import project.appRace.models.User;
import project.appRace.models.User.Role;
import project.appRace.repositories.UserRepository;

@Configuration
@Profile("!test")
public class GerenteConfig implements CommandLineRunner {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public GerenteConfig(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args){
        var gerente = repository.findByEmail("gerente@gmail.com");
        if (gerente.isEmpty()) {
            var novoGerente = User.builder()
                            .email("gerente@gmail.com")
                            .name("gerente")
                            .roles(Set.of(Role.GERENTE))
                            .password(passwordEncoder.encode("123"))
                            .build();
            repository.save(novoGerente);
            System.out.println("Gerente criado com sucesso!");
        } else {
            System.out.println("Gerente ja existente!");
        }
    }

}
