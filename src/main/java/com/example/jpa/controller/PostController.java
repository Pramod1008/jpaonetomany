package com.example.jpa.controller;

import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public Page<Post>  getAllPosts(Pageable pageable)
    {
       return postRepository.findAll(pageable);
    }

    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post){
        return postRepository.save(post);
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable Long id,@Valid @RequestBody Post postRequest){
       /* return postRepository.findById(id).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(()->new RuntimeException("Post id "+id+" not found"));*/
       Post post=postRepository.findById(id).orElseThrow(()->new RuntimeException("Post id "+id+" not found"));

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());

        return postRepository.save(post);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        return postRepository.findById(postId).map(
                post ->{ postRepository.delete(post);
        return ResponseEntity.ok().build();
    }).orElseThrow(()->new RuntimeException("Post id "+postId+" Not Found"));
    }
}
