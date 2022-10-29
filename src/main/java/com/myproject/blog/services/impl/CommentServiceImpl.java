package com.myproject.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.blog.DTO.CommentDto;
import com.myproject.blog.exceptions.ResourceNotFoundException;
import com.myproject.blog.models.Comment;
import com.myproject.blog.models.Post;
import com.myproject.blog.repositories.CommentRepo;
import com.myproject.blog.repositories.PostRepo;
import com.myproject.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {
	
	
	@Autowired
	private PostRepo postrepo;

	@Autowired
	private CommentRepo commentrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post=this.postrepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId", postId));
		
		Comment comment = this.modelmapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		Comment savedcomment = this.commentrepo.save(comment);
		
		return this.modelmapper.map(savedcomment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		
		Comment com=this.commentrepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentID", commentId));
		
		this.commentrepo.delete(com);

	}

}
