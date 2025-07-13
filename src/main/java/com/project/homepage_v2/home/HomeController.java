package com.project.homepage_v2.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.homepage_v2.cmmn.enums.Menu;
import com.project.homepage_v2.home.vo.LatestPostGetVo;

import lombok.RequiredArgsConstructor;

import static com.project.homepage_v2.cmmn.Const.*;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
	private final HomeService service;
	
	@GetMapping
	public String home(Model model) {
		List<LatestPostGetVo> latestPostGet = service.latestPostGet();
		
		for(LatestPostGetVo vo : latestPostGet) {
			String icode = vo.getIcode();
			Menu menu = Menu.fromCode(icode);
			String boardUrl = menu.URL;
			
			vo.setBoardUrl(boardUrl);
		}

		model.addAttribute(DATA, latestPostGet);
		
		return "home";
	}
}