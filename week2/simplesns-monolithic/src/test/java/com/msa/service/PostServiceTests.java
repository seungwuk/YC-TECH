package com.msa.service;

import com.msa.post.domain.Post;
import com.msa.post.repository.PostRepository;
import com.msa.post.service.PostService;
import com.msa.post.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void init() {
        postService = new PostServiceImpl(postRepository);
    }

    @Test
    @DisplayName("add 시 repository 가 호출되는 지 확인")
    public void test_post_add() {
        postService.addPost("test title", "test content");
        verify(postRepository, atLeastOnce()).save(any());
    }
    
    @Test
    @DisplayName("전체 post 조회 - 내림차순")
    public void test_get_posts() {

        Post post1 = new Post(LocalDate.now().minusDays(2));
        Post post2 = new Post(LocalDate.now().plusDays(2));
        List<Post> stubPosts = List.of(post1, post2);

        // stubbing
        when(postRepository.findAll()).thenReturn(
                stubPosts
        );

        List<Post> posts = postService.getPostList();

        assertTrue(isSortedDescending(posts));
    }

    private boolean isSortedDescending(List<Post> items) {
        return IntStream.range(0, items.size() - 1)
                .noneMatch(i -> items.get(i).getCreatedAt().isBefore(items.get(i + 1).getCreatedAt()));
    }
}
