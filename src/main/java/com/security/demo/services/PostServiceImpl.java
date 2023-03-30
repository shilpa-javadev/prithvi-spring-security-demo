package com.security.demo.services;

import com.security.demo.domain.PostDTO;
import com.security.demo.domain.PostRequest;
import com.security.demo.model.Post;
import com.security.demo.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    public PostDTO addPost(PostRequest postRequest){
        Post post = postRepo.saveAndFlush(Post.builder()
                .body(postRequest.getBody())
                .title(postRequest.getTitle())
                .slug(postRequest.getSlug()).build());
        return PostDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .slug(post.getSlug())
                        .body(post.getBody())
                        .build();
    }

    public void removePost(Integer postId){
        try{
            postRepo.deleteById(postId);
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException(e.getMessage(),1);
        }
    }

    public PostDTO editPost(Integer postId,PostRequest postRequest){
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty())
            throw new EntityNotFoundException("No Post found with this ID");
        else{
            post.get().setBody(postRequest.getBody());
            post.get().setTitle(postRequest.getTitle());
            post.get().setSlug(postRequest.getSlug());
        }
        return PostDTO.builder()
                .body(post.get().getBody())
                .slug(post.get().getSlug())
                .title(post.get().getTitle())
                .id(post.get().getId())
                .build();
    }

    public PostDTO readPost(Integer postId){
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty())
            throw new EntityNotFoundException("No Post found with this ID");
        return PostDTO.builder()
                .body(post.get().getBody())
                .slug(post.get().getSlug())
                .title(post.get().getTitle())
                .id(post.get().getId())
                .build();
    }

    public List<PostDTO> readPosts(){
        List<Post> posts = postRepo.findAll();
        if(posts.isEmpty())
            throw new EntityNotFoundException("No Posts found in the database");
        return posts.stream().map(post -> {
            return PostDTO.builder()
                    .body(post.getBody())
                    .slug(post.getSlug())
                    .title(post.getTitle())
                    .id(post.getId())
                    .build();
        }).collect(Collectors.toList());
    }
}
