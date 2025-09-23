package project.appRace.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import project.appRace.dto.follow.FollowDto;
import project.appRace.dto.follow.FollowResponseDto;
import project.appRace.dto.user.UserResponseDto;
import project.appRace.models.Follow;
import project.appRace.models.User;
import project.appRace.repositories.FollowRepository;
import project.appRace.repositories.UserRepository;

@Service
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowResponseDto followUser(FollowDto dto) {
        if (dto.followerId().equals(dto.followedId())) {
            throw new IllegalArgumentException("Um usuário não pode seguir a si mesmo.");
        }

        if (followRepository.existsByFollowerIdAndFollowedId(dto.followerId(), dto.followedId())) {
            throw new IllegalStateException("Você já está seguindo este usuário.");
        }

        User follower = userRepository.findById(dto.followerId())
                .orElseThrow(() -> new RuntimeException("Usuário seguidor não encontrado"));

        User followed = userRepository.findById(dto.followedId())
                .orElseThrow(() -> new RuntimeException("Usuário a ser seguido não encontrado"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        follow.setActive(true);

        followRepository.save(follow);
        return new FollowResponseDto(follow);
    }

    public List<UserResponseDto> listarSeguindo(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com id: " + userId));
        return followRepository.findFollowingByFollowerId(userId)
                .stream()
                .map(u -> new UserResponseDto(u))
                .toList();
    }

    public List<UserResponseDto> getFollowers(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com id: " + userId));
        return followRepository.findFollowersByFollowedId(userId)
                .stream()
                .map(u -> new UserResponseDto(u))
                .toList();
    }

    public void unfollowUser(FollowDto dto) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(dto.followerId(), dto.followedId())
                .orElseThrow(() -> new IllegalStateException("Você não está seguindo este usuário."));

        followRepository.delete(follow);
    }

    public void blockUser(FollowDto dto) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(dto.followerId(), dto.followedId())
                .orElseThrow(() -> new IllegalStateException("Você não está seguindo este usuário."));

        follow.setActive(false);
        followRepository.save(follow);
    }

    public void unblockUser(FollowDto dto) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(dto.followerId(), dto.followedId())
                .orElseThrow(() -> new IllegalStateException("Você não está seguindo este usuário."));

        follow.setActive(true);
        followRepository.save(follow);
    }

}
