package com.rws.user;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"})
@JsonFilter("UserInfo")
public class User {
	private long Id;

	@Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
	private String name;

	@Past
	private Date joinDate;

	//@JsonIgnore // 반환 결과에서 제외
	private String password;
	private String ssn;
}
