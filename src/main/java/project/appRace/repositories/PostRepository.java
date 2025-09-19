package project.appRace.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.appRace.models.Post;
import project.appRace.models.User;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getByCreator(User creator);
}
