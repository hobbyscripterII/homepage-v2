package com.project.homepage_v2.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.homepage_v2.board.dto.BoardCntGetDto;
import com.project.homepage_v2.board.dto.BoardGetDto;
import com.project.homepage_v2.board.vo.BoardGetVo;
import com.project.homepage_v2.board.vo.BoardSelVo;
import com.project.homepage_v2.board.vo.NextPostGetVo;
import com.project.homepage_v2.board.vo.PrevPostGetVo;

import static com.project.homepage_v2.cmmn.Const.*;

import com.project.homepage_v2.cmmn.Pagination;
import com.project.homepage_v2.cmmn.enums.Menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/b")
public class BoardController {
	private BoardService service;
	private String cssVersion;

	public BoardController(@Value("${css.version}") String cssVersion, BoardService service) {
		this.service = service;
		this.cssVersion = cssVersion;
	}
	
	@GetMapping("/{url}")
	public String board(@PathVariable("url") String url, 
						@RequestParam(name = "page", defaultValue = "1", required = true) int page, 
						Model model) {
		Menu menu = Menu.fromUrl(url);
		String icode = menu.CODE;
		String boardName = menu.NAME;
		
		int amount = 5;
		int offset = getOffset(page, amount);
		
		// 게시글 정보
		BoardGetDto boardGetDto = new BoardGetDto(icode, offset, amount);
		List<BoardGetVo> boardGet = service.boardGet(boardGetDto);
		
		// 페이지네이션
		BoardCntGetDto boardCntGetDto = new BoardCntGetDto(icode, null); // 게시판 코드, 사용자 권한
		int boardCntGet = service.boardCntGet(boardCntGetDto);
		Pagination pagination = new Pagination(page, amount, boardCntGet);
		
		model.addAttribute(DATA, boardGet);
		model.addAttribute(BOARD_NAME, boardName);
		model.addAttribute(BOARD_URL, url);
		model.addAttribute(PAGINATION, pagination);
		
		return "board/" + cssVersion + "/board";
	}
	
	@GetMapping("/{url}/{iboard}")
	public String boardDetail(@PathVariable("url") String url, 
							  @PathVariable("iboard") int iboard, 
							  Model model) {
		Menu menu = Menu.fromUrl(url);
		String boardName = menu.NAME;
		
		BoardSelVo boardSel = service.boardSel(iboard);
		PrevPostGetVo prevPostGet = service.prevPostGet(iboard);
		NextPostGetVo nextPostGet = service.nextPostGet(iboard);
		
		model.addAttribute(DATA, boardSel);
		model.addAttribute(PREV_POST, prevPostGet);
		model.addAttribute(NEXT_POST, nextPostGet);
		
		model.addAttribute(BOARD_NAME, boardName);
		model.addAttribute(BOARD_URL, url);
		
		return "board/" + cssVersion + "/boardDetail";
	}
	
	private int getOffset(int page, int amount) {
		return (page == 1 ? 0 : (page -1) * amount);
	}
}