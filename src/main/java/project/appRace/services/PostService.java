package project.appRace.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import project.appRace.dto.post.PostAndCreatorResponseDto;
import project.appRace.dto.post.PostCreateDto;
import project.appRace.dto.post.PostResponseDto;
import project.appRace.dto.user.UserResponseDto;
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

    public PostAndCreatorResponseDto getPostByCreatorId(Long creatorId){
        User user = userRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("User não com encontrado com o id: " + creatorId));
        List<Post> posts = postRepository.getByCreator(user);
        if (posts.isEmpty()) {
            throw new IllegalArgumentException("Post não encontrado com o creador de id: " + creatorId);
        }

        List<PostResponseDto> postDtos = posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostAndCreatorResponseDto(new UserResponseDto(user), postDtos);
    }

}
