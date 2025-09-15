package project.appRace.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.appRace.models.User;
import project.appRace.models.User.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    /*@Query("SELECT new pointdog.com.dto.user.UserResponseDto(u.email, u.name, u.telefone, u.provider, " +
           "new pointdog.com.dto.user.UserResponseDto$EnderecoResponseDto(u.endereco.logradouro, u.endereco.bairro, u.endereco.numero, u.endereco.cep)) " +
           "FROM User u JOIN u.roles r WHERE r = :role") */
           //Page<UserResponseDto> findByRoles(@Param("role") Role role, Pageable pageable);
    Page<User> findByRoles(@Param("role") Role role, Pageable pageable);
    boolean existsByEmail(String email);
}
