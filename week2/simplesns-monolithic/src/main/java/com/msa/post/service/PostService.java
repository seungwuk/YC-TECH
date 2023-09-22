package com.msa.post.service;

import java.util.List;

import com.msa.post.domain.Post;

public interface PostService {
	
	Post addPost(String title, String content);
	
	Post getPost(Long id);
	
	List<Post> getPostListByUserId();

	List<Post> getPostList();
}
