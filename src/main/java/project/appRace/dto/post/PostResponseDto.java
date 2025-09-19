package project.appRace.dto.post;

import project.appRace.models.Post;

public record PostResponseDto(
        String urlPhoto,
        String titulo,
        String descricao) {
    public PostResponseDto(Post post) {
        this(post.getUrlPhoto(), post.getTitulo(), post.getDescricao());
    }
}
