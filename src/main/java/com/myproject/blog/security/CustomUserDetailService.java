package com.myproject.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.blog.exceptions.ResourceNotFoundException;
import com.myproject.blog.models.User;
import com.myproject.blog.repositories.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//loading user from database by username 
		User user=this.userepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email "+username,0));
		
		return user;
	}

}
