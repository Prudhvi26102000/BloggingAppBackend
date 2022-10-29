package com.myproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.blog.models.Category;


public interface CategoryRepo extends JpaRepository<Category,Integer>{

	@Query("SELECT u FROM Category u WHERE u.id = ?1")
    public Category findById(int id);
}
