package com.msa.post.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msa.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Serializable> {

	List<Post> findByOrderByIdDesc();
	
	List<Post> findAllByUserId(Long userId);

	List<Post> findByIdInOrderByIdDesc(List<Long> postIdList);

}
