package com.project.homepage_v2.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.homepage_v2.admin.dto.AdminBoardCntGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardInsDto;
import com.project.homepage_v2.admin.dto.AdminBoardUpdDto;
import com.project.homepage_v2.admin.vo.AdminBoardGetVo;
import com.project.homepage_v2.admin.vo.AdminBoardInsVo;
import com.project.homepage_v2.api.ApiResponse;
import com.project.homepage_v2.board.vo.BoardSelVo;
import com.project.homepage_v2.cmmn.Pagination;
import com.project.homepage_v2.cmmn.ResultVo;
import com.project.homepage_v2.cmmn.enums.Menu;

import static com.project.homepage_v2.api.ApiStatus.*;
import static com.project.homepage_v2.cmmn.Const.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/a")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService service;
	
	@GetMapping("/{url}")
	public String adminBoardGet(@PathVariable("url") String url,
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

	@GetMapping("/etc")
	public String adminEtc(Model model) {
		return "admin/adminEtc";
	}
	
	@GetMapping("/i/{url}")
	public String adminBoardInsert(@PathVariable("url") String url, Model model) {
		log.info("url = {}", url);
		
		model.addAttribute(BOARD_URL, url);
		
		return "board/boardInsert";
	}
	
	@GetMapping("/u/{iboard}")
	public String adminBoardUpdate(@PathVariable("iboard") int iboard, Model model) {
		BoardSelVo boardSel = service.adminBoardSel(iboard);
		String icode = boardSel.getIcode();
		String url = Menu.fromCode(icode).getURL();
		String name = Menu.fromCode(icode).getNAME();
		
		model.addAttribute(DATA, boardSel);
		model.addAttribute(BOARD_URL, url);
		model.addAttribute(BOARD_NAME, name);
		
		return "board/boardInsert";
	}
	
	@ResponseBody
	@PostMapping("/i")
	public ResponseEntity<ApiResponse<AdminBoardInsVo>> adminBoardInsert(@RequestPart(name = "thumbnail", required = false) MultipartFile mf,
															   			 @RequestPart(name = "dto") AdminBoardInsDto dto) {
		ApiResponse<AdminBoardInsVo> apiResponse = null;
		
		try {
			service.adminBoardIns(mf, dto);
			
			int iboard = dto.getIboard();
			AdminBoardInsVo vo = new AdminBoardInsVo(iboard);
			
			apiResponse = ApiResponse.success(vo);
		} catch(IOException | SQLException e) {
			apiResponse = ApiResponse.error(INTERNAL_SERVER_ERROR.getStatusCode(), 
											INTERNAL_SERVER_ERROR.getMessage(), 
											e.getMessage());
		}
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@ResponseBody
	@PatchMapping("/u/{iboard}")
	public ResponseEntity<ApiResponse<ResultVo>> adminBoardUpdate(@PathVariable("iboard") int iboard,
																  @RequestPart(name = "thumbnail", required = false) MultipartFile mf,
																  @RequestPart(name = "dto") AdminBoardUpdDto dto) {
		ApiResponse<ResultVo> apiResponse = null;

		try {
			dto.setIboard(iboard);

			int adminBoardUpdRows = service.adminBoardUpd(mf, dto);
			ResultVo vo = new ResultVo(adminBoardUpdRows);

			apiResponse = ApiResponse.success(vo);
		} catch (IOException | SQLException e) {
			apiResponse = ApiResponse.error(INTERNAL_SERVER_ERROR.getStatusCode(),
											INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(apiResponse);
	}
	
	@ResponseBody
	@DeleteMapping("/{url}/{iboard}")
	public ResponseEntity<ApiResponse<ResultVo>> adminBoardDelete(@PathVariable("url") String url,
																  @PathVariable("iboard") int iboard) {
		ApiResponse<ResultVo> apiResponse = null;

		try {
			int boardDelRows = service.adminBoardDel(iboard);
			ResultVo vo = new ResultVo(boardDelRows);

			apiResponse = ApiResponse.success(vo);
		} catch (SQLException e) {
			apiResponse = ApiResponse.error(INTERNAL_SERVER_ERROR.getStatusCode(),
											INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(apiResponse);
	}

	@ResponseBody
	@DeleteMapping("/rm")
	public ResponseEntity<ApiResponse<ResultVo>> adminBoardRemoveFileTask() {
		ApiResponse<ResultVo> apiResponse = null;

		try {
			service.adminBoardRemoveFileTask();
			
			ResultVo vo = new ResultVo(SUCCESS_CODE);
			apiResponse = ApiResponse.success(vo);
		} catch (IOException e) {
			apiResponse = ApiResponse.error(INTERNAL_SERVER_ERROR.getStatusCode(),
											INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
		}
		
		return ResponseEntity.ok(apiResponse);
	}
	
	private int getOffset(int page, int amount) {
		return (page == 1 ? 0 : (page - 1) * amount);
	}
}