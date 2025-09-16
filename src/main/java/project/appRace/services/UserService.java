package project.appRace.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import project.appRace.dto.user.UserCreateDto;
import project.appRace.dto.user.UserResponseDto;
import project.appRace.mappers.UserMapper;
import project.appRace.models.User;
import project.appRace.models.User.Role;
import project.appRace.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;  

    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new project.appRace.exceptions.EmailAlreadyExistsException("Email já está cadastrado");
        }
        User user = userMapper.toEntity(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.password()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findByRoles(Role.CLIENTE, pageable);
        return users.map(UserResponseDto::new);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado: " + id));
        return new UserResponseDto(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("Usuário não encontrado: " + id);
        }
        userRepository.deleteById(id);
    }



}
