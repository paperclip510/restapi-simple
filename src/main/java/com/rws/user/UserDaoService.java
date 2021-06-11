package com.rws.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<>();

	static {
		users.add(new User(1, "kdh", new Date()));
		users.add(new User(2, "kdh2", new Date()));
		users.add(new User(3, "kdh3", new Date()));
	}

	// 사용자리스트 조회
	public List<User> findAll() {
		return users;
	}

	// 특정 사용자 조회
	public User findOne(long id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	// 사용자 추가
	public User save(User user) {
		if (user.getId() == 0) {
			user.setId(users.size() + 1);
		}
		users.add(user);
		return user;
	}

	// 사용자 삭제
	public User deleteById(long id) {
		Iterator<User> iterator = users.iterator();

		while (iterator.hasNext()) {
			User user = iterator.next();

			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}

		return null;
	}

	// 사용자 수정
	public User editById(User targetUser) {
		long targetUserId = targetUser.getId();
		
		for (User user : users) {
			if (user.getId() == targetUserId) {
				user.setName(targetUser.getName());
				return user;
			}
		}
		
		return null;

	}
}
