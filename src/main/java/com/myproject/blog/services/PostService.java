package com.myproject.blog.services;

import java.util.List;

import com.myproject.blog.DTO.PostDto;
import com.myproject.blog.DTO.PostResponse;


public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	PostDto getPostById(Integer postId);
	void deletePost(Integer postId);
	List<PostDto> getAllPosts();
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	List<PostDto> getPostsByCategory(Integer categoryId);
	List<PostDto> getPostByUser(Integer userId);
	List<PostDto> searchPosts(String keyword);
}
