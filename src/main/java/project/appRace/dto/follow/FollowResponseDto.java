package project.appRace.dto.follow;

import project.appRace.dto.user.UserResponseDto;
import project.appRace.models.Follow;

public record FollowResponseDto(UserResponseDto follower, UserResponseDto following) {
    public FollowResponseDto(Follow follow) {
        this(new UserResponseDto(follow.getFollower()), new UserResponseDto(follow.getFollowed()));
    }
}
