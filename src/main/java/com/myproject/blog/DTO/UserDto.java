package com.myproject.blog.DTO;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	
	@NotEmpty(message="Username must not be empty" )
	@Size(min=4,message="Username must be minimum of 4 characters !! ")
	private String name;
	
	@Email(message="Email is not valid !! ")
	private String email;
	
	@NotEmpty(message="Password must not be empty" )
	@Size(min=3,max=14,message="Password must be min of 3 chars and max of 14 chars !!")
	private String password;
	
	@NotEmpty(message="About must not be empty" )
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();

}
