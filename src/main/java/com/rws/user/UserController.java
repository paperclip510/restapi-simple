package com.rws.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class UserController {
	private UserDaoService service;
	
	//생성자로 의존선 주입. autowired
	public UserController(UserDaoService service) {
		this.service = service;	
	}

	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable long id) {
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		//filter
//		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//				.filterOutAllExcept("id", "name", "joinDate");
//
//		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
//
//		MappingJacksonValue mapping = new MappingJacksonValue(user);
//		mapping.setFilters(filters);
		
		
		// hateoas
		// Resource -> EntityModel
		// ControllerLinkBuilder -> WebMvcLinkBuilder
		
		EntityModel<User> entityModel = new EntityModel<>(user);
		//Resource<User> resource = new Resource<Uscer>(user);
		
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		
		entityModel.add(linkTo.withRel("all-users"));
		
		return entityModel;
	}

	@PostMapping(path="/users")//form 형태가 아닌 json 형태를 받기 위해서는 RequestBody어노테이션 선언
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		
		//201 created
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable long id) {
		User user = service.deleteById(id);
		
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		
	}
	
	@PutMapping(path="/users")
	public void editById(@RequestBody User user) {
		User Editeduser = service.editById(user);
		
		if(Editeduser == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}









