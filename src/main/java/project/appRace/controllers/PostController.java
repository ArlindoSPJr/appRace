package project.appRace.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.appRace.dto.post.PostAndCreatorResponseDto;
import project.appRace.dto.post.PostCreateDto;
import project.appRace.dto.post.PostResponseDto;
import project.appRace.services.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateDto postDto) {
        PostResponseDto response = postService.createPost(postDto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<PostAndCreatorResponseDto> getPostByCreatorId(@Valid @PathVariable Long id) {
        PostAndCreatorResponseDto response = postService.getPostByCreatorId(id);
        return ResponseEntity.ok(response);
    }

}
