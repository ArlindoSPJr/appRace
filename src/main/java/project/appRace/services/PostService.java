package project.appRace.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import project.appRace.dto.post.PostCreateDto;
import project.appRace.dto.post.PostResponseDto;
import project.appRace.models.Post;
import project.appRace.models.User;
import project.appRace.repositories.PostRepository;
import project.appRace.repositories.UserRepository;

@Service
@AllArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(PostCreateDto postDto) {
        User creator = userRepository.findById(postDto.creatorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + postDto.creatorId()));
        
        Post post = Post.builder()
                .creator(creator)
                .urlPhoto(postDto.urlPhoto())
                .titulo(postDto.titulo())
                .descricao(postDto.descricao())
                .build();
        postRepository.save(post);
        return new PostResponseDto(post);
    }

}
