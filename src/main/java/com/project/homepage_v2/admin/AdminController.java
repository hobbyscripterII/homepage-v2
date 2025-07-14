package com.project.homepage_v2.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.homepage_v2.admin.dto.AdminBoardCntGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardGetDto;
import com.project.homepage_v2.admin.vo.AdminBoardGetVo;
import com.project.homepage_v2.admin.vo.MenuCodeGetVo;
import com.project.homepage_v2.cmmn.Pagination;
import com.project.homepage_v2.cmmn.enums.Menu;

import static com.project.homepage_v2.cmmn.Const.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/a")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService service;
	
	@GetMapping("/i")
	public String adminBoardInsert(Model model) {
//		List<MenuCodeGetVo> menuCodeGet = service.menuCodeGet();
		
//		model.addAttribute(MENU_LIST, menuCodeGet);
		
		return "board/boardInsert";
	}
	
	@GetMapping("/{url}")
	public String admin(@PathVariable("url") String url,
						@RequestParam(name = "page", defaultValue = "1", required = true) int page,
						Model model) {
		int amount = 10;
		int offset = getOffset(page, amount);
		Menu menu = Menu.fromUrl(url);
		String icode = menu.CODE;
		AdminBoardGetDto dto = new AdminBoardGetDto(icode, offset, amount);
		
//		List<MenuCodeGetVo> menuCodeGet = service.menuCodeGet();
		List<AdminBoardGetVo> adminBoardGet = service.adminBoardGet(dto);
		
		AdminBoardCntGetDto adminBoardCntGetDto = new AdminBoardCntGetDto(icode, null);
		int adminBoardCntGet = service.adminBoardCntGet(adminBoardCntGetDto);
		Pagination pagination = new Pagination(page, amount, adminBoardCntGet);
		
		model.addAttribute(DATA, adminBoardGet);
//		model.addAttribute(MENU_LIST, menuCodeGet);
		model.addAttribute(PAGINATION, pagination);
		model.addAttribute(BOARD_URL, url);
		
		return "admin/admin";
	}
	
	private int getOffset(int page, int amount) {
		return (page == 1 ? 0 : (page -1) * amount);
	}
}