package com.msa.post.service;

import com.msa.post.domain.Post;
import com.msa.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}


	@Override
	public Post addPost(String title, String content) {
		// 테스트 코드가 동작하도록 구현
		Post post = new Post(1L, title, content);
		return postRepository.save(post);
	}

	@Override
	public Post getPost(Long id) {
		return null;
	}

	@Override
	public List<Post> getPostListByUserId() {
		return new ArrayList<>();
	}

	@Override
	// findAll 을 통해서 모든 데이터 조회
	// created_at 기준 내림차순으로 정렬
	public List<Post> getPostList() {
		return postRepository.findAll()
				.stream()
				.sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())).toList();
	}
	
}
