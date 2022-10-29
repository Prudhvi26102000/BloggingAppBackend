package com.myproject.blog.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.myproject.blog.models.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
    public User findById(int id);
	
	Optional<User> findByEmail(String email);

}
