package com.myproject.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myproject.blog.DTO.PostDto;
import com.myproject.blog.DTO.PostResponse;
import com.myproject.blog.exceptions.ResourceNotFoundException;
import com.myproject.blog.models.Category;
import com.myproject.blog.models.Post;
import com.myproject.blog.models.User;
import com.myproject.blog.repositories.CategoryRepo;
import com.myproject.blog.repositories.PostRepo;
import com.myproject.blog.repositories.UserRepo;
import com.myproject.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postrepo;

	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user=this.userrepo.findById(userId)
				.orElseThrow(() ->new ResourceNotFoundException("User","id",userId));
		
		Category category=this.categoryrepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		Post post=this.modelmapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);

		Post newPost=this.postrepo.save(post);
		
		return this.modelmapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) 
	{
		Post post=this.postrepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","post Id",postId));
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		post.setContent(postDto.getContent());
		
		Post updatedPot=this.postrepo.save(post);
		return this.modelmapper.map(updatedPot,PostDto.class);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postrepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","post Id",postId));
		
		return this.modelmapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postrepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","post Id",postId));
		
		this.postrepo.delete(post);

	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts=this.postrepo.findAll();
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
		
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePost=this.postrepo.findAll(p);
		
		List<Post> posts=pagePost.getContent();
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postresponse=new PostResponse();
		
		postresponse.setContent(postDtos);
		postresponse.setPageNumber(pagePost.getNumber());
		postresponse.setPageSize(pagePost.getSize());
		postresponse.setTotalElements(pagePost.getTotalElements());
		postresponse.setTotalPages(pagePost.getTotalPages());
		postresponse.setLastPage(pagePost.isLast());
		
		return postresponse;
	}
	
	
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat=this.categoryrepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","category Id",categoryId));
		
		List<Post> posts=this.postrepo.findByCategory(cat);
		
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user=this.userrepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","user ID",userId));
		
		List<Post> posts=this.postrepo.findByUser(user);
		
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> posts=this.postrepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
