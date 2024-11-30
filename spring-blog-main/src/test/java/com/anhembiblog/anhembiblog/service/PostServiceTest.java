package com.anhembiblog.anhembiblog.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.anhembiblog.anhembiblog.model.Post;
import com.anhembiblog.anhembiblog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PostRepo repo;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um Post de forma sucedida quando tudo estiver ok")
    void createPostCase1() {
        Post post = new Post(1L, "Primeiro");
        User user = new User(2L, "Teste");

        when(userService.findById(2L)).thenReturn(Optional.of(user));
        when(repo.save(any(Post.class))).thenReturn(post);
        when(postService.authorizePost(user, post)).thenReturn(true);

        Post createdPost = postService.createPost(post, user);
        assertNotNull(createdPost);
        assertEquals("Primeiro", createdPost.getTitle());
    }

    @Test
    @DisplayName("Deve mostrar que o Post não foi criado de maneira sucedida")
    void createPostCase2() {
        Post post = new Post(1L, "Primeiro");
        User user = new User(2L, "Teste");

        when(postService.authorizePost(user, post)).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> {
            postService.createPost(post, user);
        });

        assertEquals("Post não autorizado", thrown.getMessage());
    }
}