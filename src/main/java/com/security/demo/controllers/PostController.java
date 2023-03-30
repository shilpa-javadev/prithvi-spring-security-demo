package com.security.demo.controllers;

import com.security.demo.domain.PostDTO;
import com.security.demo.domain.PostRequest;
import com.security.demo.domain.UserDTO;
import com.security.demo.domain.UserRequest;
import com.security.demo.repo.PostRepo;
import com.security.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/add-post")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostRequest postRequest){
        PostDTO post = postService.addPost(postRequest);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/read-post/{postId}")
    public ResponseEntity<PostDTO> readPost(@PathVariable String postId){
        PostDTO post = postService.readPost(Integer.valueOf(postId));
        return ResponseEntity.ok(post);
    }

    @GetMapping("/read-posts")
    public ResponseEntity<List<PostDTO>> readPosts(){
        List<PostDTO> posts = postService.readPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/remove-post/{postId}")
    public ResponseEntity<PostDTO> removePost(@PathVariable String postId){
        postService.removePost(Integer.valueOf(postId));
        return ResponseEntity.accepted().build();
    }
    @PutMapping("/edit-post/{postId}")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostRequest postRequest,@PathVariable String postId){
        PostDTO post = postService.editPost(Integer.valueOf(postId),postRequest);
        return ResponseEntity.ok(post);
    }
}
