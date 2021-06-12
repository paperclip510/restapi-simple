package com.rws.user;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
	// GET /admin/v1/users/1 -> /admin/v1/users/1
	//@GetMapping(path = "/v1/users/{id}")
	//@GetMapping(path = "/users/{id}/", params = "version=1")
	//@GetMapping(value="/users/{id}", headers="X-API-VERSION=1")
	@GetMapping(path = "/users/{id}", produces = "application/vnd.company.appv1+json")
	public MappingJacksonValue retrieveUserV1(@PathVariable long id) {
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

	
	//@GetMapping(path = "/v2/users/{id}")
	//@GetMapping(path = "/users/{id}/", params = "version=2")
	//@GetMapping(value="/users/{id}", headers="X-API-VERSION=2")
	@GetMapping(path = "/users/{id}", produces = "application/vnd.company.appv2+json")
	public MappingJacksonValue retrieveUserV2(@PathVariable long id) {
		User user = service.findOne(id);
		
		
		if (user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}

		// User -> User2
		UserV2 userV2 = new UserV2();
		BeanUtils.copyProperties(user, userV2);
		userV2.setGrade("VIP");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "name", "joinDate", "grade");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo2", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(userV2);
		mapping.setFilters(filters);

	
		return mapping;
	}
	

	

	
	
	
	
	
	
	
	
	
	
}
