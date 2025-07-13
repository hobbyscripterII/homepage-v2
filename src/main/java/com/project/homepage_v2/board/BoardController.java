package com.project.homepage_v2.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.homepage_v2.board.dto.BoardGetDto;
import com.project.homepage_v2.board.vo.BoardGetVo;
import static com.project.homepage_v2.cmmn.Const.*;
import com.project.homepage_v2.cmmn.enums.Menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/b")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService service;
	
	@GetMapping("/{url}")
	public String board(@PathVariable("url") String url, 
						@RequestParam(name = "page", defaultValue = "1", required = true) int page, 
						Model model) {
		int amount = 10;
		Menu menu = Menu.getMenu(url);
		String icode = menu.CODE;
		String boardName = menu.NAME;
		int offset = getOffset(page, amount);
		BoardGetDto boardGetDto = new BoardGetDto(icode, offset, amount);
		List<BoardGetVo> boardGet = service.boardGet(boardGetDto);
		
		model.addAttribute(DATA, boardGet);
		model.addAttribute(BOARD_NAME, boardName);
		
		return "board/board";
	}
	
	private int getOffset(int page, int amount) {
		return (page == 1 ? 0 : (page -1) * amount);
	}
}