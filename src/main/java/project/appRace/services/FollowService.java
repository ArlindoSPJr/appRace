package project.appRace.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import project.appRace.dto.follow.FollowDto;
import project.appRace.dto.follow.FollowResponseDto;
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
    
    followRepository.save(follow);
    return new FollowResponseDto(follow);
}

}
