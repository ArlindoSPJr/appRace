package project.appRace.dto.post;

import project.appRace.dto.user.UserResponseDto;
import project.appRace.models.Post;

public record PostResponseDto(UserResponseDto creator,
        String urlPhoto,
        String titulo,
        String descricao) {
    public PostResponseDto(Post post) {
        this(new UserResponseDto(post.getCreator()), post.getUrlPhoto(), post.getTitulo(), post.getDescricao());
    }
}
