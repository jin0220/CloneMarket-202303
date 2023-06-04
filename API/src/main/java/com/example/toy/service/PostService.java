package com.example.toy.service;

import com.example.toy.entity.Post;
import com.example.toy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getPost() {
        return postRepository.findAll();
    }

    public boolean setPost(Post post) {
        postRepository.save(post);

        return true;
    }

}
