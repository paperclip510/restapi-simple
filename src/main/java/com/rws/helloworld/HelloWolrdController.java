package com.rws.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWolrdController {

	// DI Annotation 
	// 현재 스프링 프레임워크에서 록된 빈중 같은 타입의 빈을 등록
	@Autowired
	private MessageSource messageSource;

	// GET
	// /hello-wolrd (endpoint)
	// @RequestMapping()
	@GetMapping(path = "/hello-world")
	public String helloWorld() {

		return "hello world!";
	}

	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world!");
	}

	@GetMapping(path = "/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("hello world %s!", name));
	}

	@GetMapping(path = "/hello-world-internationlized")
	public String helloWorldInternationalized(
			@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		return messageSource.getMessage("greeting.message", null,locale);
	}

}
