package project.appRace.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.appRace.models.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followingId);

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);
    
}
