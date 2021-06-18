package com.rws.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path="/jpa")
public class UserJpaController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;


	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		List<User> users = new ArrayList<>();
		
		users = userRepository.findAll();
		
		return users;
	}
	
	@GetMapping(path="/users/{id}")
	public EntityModel<User> retrieveUsersById(@PathVariable long id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found",id));
		}
		
		//return user.get();
		
		// hateoas
		// Resource -> EntityModel
		// ControllerLinkBuilder -> WebMvcLinkBuilder
		
		EntityModel<User> entityModel = new EntityModel<>(user.get());
		//Resource<User> resource = new Resource<Uscer>(user);
		
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		
		entityModel.add(linkTo.withRel("all-users"));

		return entityModel;

	}

	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable long id) {
		userRepository.deleteById(id);
	}

	
	@PostMapping(path="/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
													.path("{id}")
													.buildAndExpand(savedUser.getId())
													.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	@GetMapping(path = "/users/{id}/posts")
	public List<Post> retrieveAllPostByUser(@PathVariable long id){
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found",id));
		}
		
		
		return user.get().getPost();
	}
	
	
	@PostMapping(path="/users/{id}/posts")
	public ResponseEntity<Post> createPost(@PathVariable long id,  @RequestBody Post post){
		// 사용자 정보 검색 
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found",id));
		}
		
		post.setUser(user.get());
		
		
		
		//포스트 저장 
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
													.path("{id}")
													.buildAndExpand(savedPost.getId())
													.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	
	
	
	
	


}
