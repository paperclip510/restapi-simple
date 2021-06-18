package com.rws.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	
	private String description;
	
	
	// User : Post -> 1:0..n Main : Sub
	@ManyToOne(fetch = FetchType.LAZY) //지연로딩 방식. 사용자 엔티를 조회할때 post 엔터티를 조회하는 것이 아니라 post 엔터티를 조회할때 사용자 엔티를 조회한다.
	@JsonIgnore //비공개 
	private User user;
	
	
}
