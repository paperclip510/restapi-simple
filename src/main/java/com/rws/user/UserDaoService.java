package com.rws.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	
	static {
		users.add(new User(1,"kdh",new Date()));
		users.add(new User(2,"kdh2",new Date()));
		users.add(new User(3,"kdh3",new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User findOne(long id) {
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public User save(User user) {
		if(user.getId() == 0) {
			user.setId(users.size()+1);
		}
		users.add(user);
		return user;
	}
}
