package com.myproject.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.blog.DTO.ApiResponse;
import com.myproject.blog.DTO.CategoryDto;
import com.myproject.blog.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//Create
	@PostMapping("/createcategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		
		CategoryDto createCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
	}
	
	//Update
	@PutMapping("/updatecategory/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId)
	{
		CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(updateCategory,HttpStatus.CREATED);
	}
	
	//GetAllCategories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllcategories()
	{
		List<CategoryDto> categories=this.categoryService.getCategories();
		return new ResponseEntity<>(categories,HttpStatus.OK);
		
	}
	
	//DeleteCategory
	@DeleteMapping("/deletecategory/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
	{
		this.categoryService.deletCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
	}
	
	//GetCategoryById
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> GetCategory(@PathVariable Integer categoryId)
	{
		CategoryDto category=this.categoryService.getCategory(categoryId);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
	
}
