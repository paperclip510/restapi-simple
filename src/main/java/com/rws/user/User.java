package com.rws.user;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	private long Id;
	private String name;
	private Date joinDate;
}
