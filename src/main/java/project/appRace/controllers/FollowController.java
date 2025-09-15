package project.appRace.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.appRace.dto.follow.FollowDto;
import project.appRace.dto.follow.FollowResponseDto;
import project.appRace.services.FollowService;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    
    private final FollowService followService;

    @PostMapping()
    public ResponseEntity<FollowResponseDto> followUser(@Valid @RequestBody FollowDto followDto) {
        FollowResponseDto response = followService.followUser(followDto);
        return ResponseEntity.status(201).body(response);
    }

}
