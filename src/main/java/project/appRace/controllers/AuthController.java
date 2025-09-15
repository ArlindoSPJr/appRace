package project.appRace.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import project.appRace.models.User;
import project.appRace.repositories.UserRepository;
import project.appRace.security.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email já cadastrado"));
        }
     
        User u = User.builder()
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .name(req.getName())
                .build();
        userRepo.save(u);
        return ResponseEntity.ok(Map.of("message", "Usuário registrado!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado: " + req.email));
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(Map.of("accessToken", token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "credenciais invalidas"));
        }
    }

    @Data
    public static class RegisterRequest {
        private String email;
        private String password;
        private String name;
        private String telefone;
        private AddressRequest address;
    }

    @Data
    public static class AddressRequest {
        private String street;
        private String district;
        private Integer number;
        private String cep;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}