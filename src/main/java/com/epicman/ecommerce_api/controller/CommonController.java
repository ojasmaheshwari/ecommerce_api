package com.epicman.ecommerce_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommonController {

	@GetMapping("/")
	public String isServerUp() {
		return "OK";
	}
}
