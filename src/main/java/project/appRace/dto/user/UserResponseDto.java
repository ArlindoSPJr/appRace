package project.appRace.dto.user;

import java.util.Set;

import project.appRace.models.User;
import project.appRace.models.User.Role;

public record UserResponseDto(Long id,String name, String email,Set<Role> roles) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getRoles());
    }
}
