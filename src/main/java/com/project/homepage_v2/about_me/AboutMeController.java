package com.project.homepage_v2.about_me;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {
	@GetMapping("/about_me")
	public String aboutMe() {
		return "aboutMe";
	}
}