package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class Home {

	@GetMapping("/")
	@ResponseBody
	public String HomeMessage(){
		return "Home Message Print";
	}
}
