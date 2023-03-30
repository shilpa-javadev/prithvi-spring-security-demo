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

public interface PostService {
    public PostDTO addPost(PostRequest postRequest);

    public void removePost(Integer postId);

    public PostDTO editPost(Integer postId,PostRequest postRequest);

    public PostDTO readPost(Integer postId);

    public List<PostDTO> readPosts();
}
