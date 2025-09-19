package project.appRace.dto.post;

import java.util.List;

import project.appRace.dto.user.UserResponseDto;

public record PostAndCreatorResponseDto(UserResponseDto creator, List<PostResponseDto> posts) {
    public PostAndCreatorResponseDto(UserResponseDto creator, List<PostResponseDto> posts) {
        this.creator = creator;
        this.posts = posts;
    }
}
