package com.myproject.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.blog.DTO.UserDto;
import com.myproject.blog.config.AppConstants;
import com.myproject.blog.exceptions.ResourceNotFoundException;
import com.myproject.blog.models.Role;
import com.myproject.blog.models.User;
import com.myproject.blog.repositories.RoleRepo;
import com.myproject.blog.repositories.UserRepo;
import com.myproject.blog.services.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.dtoToUser(userDto);
		User savedUser=this.userrepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userrepo.findById(userId)
				.orElseThrow(() ->new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateduser=this.userrepo.save(user);
		UserDto userDto1=this.userToDto(updateduser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=this.userrepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		UserDto userDto=this.userToDto(user);
		
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users=this.userrepo.findAll();
		
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userrepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		this.userrepo.delete(user);

	}
	
	private User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto,User.class);
		return user;
	}
	
	private UserDto userToDto(User user)
	{
		
		UserDto userdto=this.modelMapper.map(user, UserDto.class);
		
//		UserDto userdto=new UserDto();
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setPassword(user.getPassword());
//		userdto.setAbout(user.getAbout());
		return userdto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//Encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userrepo.save(user);
		return this.modelMapper.map(newUser,UserDto.class);
	}
	


}
