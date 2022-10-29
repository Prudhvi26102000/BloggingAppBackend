package com.myproject.blog.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.blog.DTO.ApiResponse;
import com.myproject.blog.DTO.CommentDto;
import com.myproject.blog.services.CommentService;

@RestController

public class CommentController {
	
	@Autowired
	private CommentService commentservice;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId)
	{
		CommentDto createComment=this.commentservice.createComment(comment, postId);
		return new ResponseEntity<>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentservice.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully !!",true),HttpStatus.OK);
	}
}
