package com.myproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
