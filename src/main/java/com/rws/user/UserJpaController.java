package com.rws.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/jpa")
public class UserJpaController {

	@Autowired
	private UserRepository userRepository;


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


	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
