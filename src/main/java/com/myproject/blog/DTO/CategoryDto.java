package com.myproject.blog.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private int categoryId;
	
	@NotBlank
	@Size(min=4,message="Min size of category Title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,message="Min size of category Description is 10")
	private String categoryDescription;
	
}
