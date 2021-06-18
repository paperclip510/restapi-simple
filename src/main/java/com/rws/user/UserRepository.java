package com.rws.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //database bean
public interface UserRepository extends JpaRepository<User, Long>{

	
}
