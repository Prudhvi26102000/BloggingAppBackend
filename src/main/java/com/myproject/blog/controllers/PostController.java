package com.myproject.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.blog.DTO.ApiResponse;
import com.myproject.blog.DTO.PostDto;
import com.myproject.blog.DTO.PostResponse;
import com.myproject.blog.config.AppConstants;
import com.myproject.blog.services.FileService;
import com.myproject.blog.services.PostService;

@RestController

public class PostController {

	@Autowired
	private PostService postservice;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
	
	//Create a Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId )
	{
		PostDto createPost=this.postservice.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<>(createPost,HttpStatus.CREATED);
	}
	
	//Get Post By User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts=this.postservice.getPostByUser(userId);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	//get Posts By Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> posts=this.postservice.getPostsByCategory(categoryId);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	//Get All Posts
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPosts()
	{
		List<PostDto> posts=this.postservice.getAllPosts();
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			)
	{
			PostResponse posts=this.postservice.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	//get Post By Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto post=this.postservice.getPostById(postId);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	 
	//Delete Post
	@DeleteMapping("/deletepost/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postservice.deletePost(postId);
		return new ApiResponse("Post Deleted Successfully !!",true);
	}
	
	
	//Update Post
	@PutMapping("/updatepost/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto updatePost=this.postservice.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost,HttpStatus.OK);
	}
	
	
	//Search Post
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword)
	{
		List<PostDto> result=this.postservice.searchPosts(keyword);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException
	{
		PostDto postDto=this.postservice.getPostById(postId);
		String fileName;
		fileName=this.fileservice.UploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPostDto=this.postservice.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value="post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response)throws IOException
	{
		InputStream resource=this.fileservice.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
}
