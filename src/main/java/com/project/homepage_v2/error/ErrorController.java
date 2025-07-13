package com.project.homepage_v2.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/error")
	public String error() {
		return "error/error";
	}
}
