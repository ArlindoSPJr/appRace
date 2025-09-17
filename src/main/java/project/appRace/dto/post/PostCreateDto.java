package project.appRace.dto.post;

public record PostCreateDto(Long creatorId,
        String urlPhoto,
        String titulo,
        String descricao) {
}
