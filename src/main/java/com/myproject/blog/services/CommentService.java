package com.myproject.blog.services;

import com.myproject.blog.DTO.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);
	
	
}
