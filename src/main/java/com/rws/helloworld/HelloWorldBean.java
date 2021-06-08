package com.rws.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok : getter, setter, toStrin 생성
//AllArgesConstructor모든 아규먼트를 가진 생성

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
	private String message;
	
}
