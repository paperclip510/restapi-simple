package com.rws.user;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping(path = "/admin")
public class AdminUserController {
	private UserDaoService service;

	// 생성자로 의존선 주입. autowired
	public AdminUserController(UserDaoService service) {
		this.service = service;
	}

	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers() {

		List<User> users = service.findAll();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "name", "joinDate", "password");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(users);
		mapping.setFilters(filters);

		return mapping;
	}

	// 관리자는 사용자의 모든 정보 조회
	@GetMapping(path = "/users/{id}")
	public MappingJacksonValue retrieveUser(@PathVariable long id) {
		User user = service.findOne(id);

		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "name", "joinDate", "password", "ssn");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);

		return mapping;
	}

	
	
	
	
	
}
