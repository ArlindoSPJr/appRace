package project.appRace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.appRace.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
