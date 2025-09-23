package project.appRace.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.appRace.models.Follow;
import project.appRace.models.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followingId);

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("SELECT f.followed FROM Follow f WHERE f.follower.id = :followerId")
    List<User> findFollowingByFollowerId(Long followerId);
    
}
