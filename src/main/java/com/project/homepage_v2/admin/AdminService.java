package com.project.homepage_v2.admin;

import static com.project.homepage_v2.cmmn.Const.IMG_SUFFIX_PATH;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.homepage_v2.admin.dto.AdminBoardCntGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardInsDto;
import com.project.homepage_v2.admin.dto.AdminBoardUpdDto;
import com.project.homepage_v2.admin.dto.ThumbnailInsDto;
import com.project.homepage_v2.admin.dto.ThumbnailUpdDto;
import com.project.homepage_v2.admin.vo.AdminBoardGetVo;
import com.project.homepage_v2.admin.vo.MenuCodeGetVo;
import com.project.homepage_v2.board.vo.BoardSelVo;
import com.project.homepage_v2.cmmn.AssertUtil;
import com.project.homepage_v2.cmmn.FileUtil;
import com.project.homepage_v2.cmmn.enums.Menu;
import com.project.homepage_v2.security.MyUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper mapper;
	private final FileUtil fileUtil;
	
	public List<AdminBoardGetVo> adminBoardGet(AdminBoardGetDto dto) {
		return mapper.adminBoardGet(dto);
	}
	
	public BoardSelVo adminBoardSel(int iboard) {
		return mapper.adminBoardSel(iboard);
	}
	
	@Transactional
	public void adminBoardIns(MultipartFile mf, AdminBoardInsDto dto) throws IOException, SQLException {
		int iadmin = iadminGet();
		String menu = dto.getMenu();
		String icode = Menu.fromUrl(menu).CODE;
		String secYn = dto.getSecYn() == "true" ? "N" : "Y";
		
		dto.setIcode(icode);
		dto.setIadmin(iadmin);
		dto.setSecYn(secYn);

		int adminBoardInsRows = mapper.adminBoardIns(dto);

		if(AssertUtil.notNull(adminBoardInsRows)) {
			if(AssertUtil.notNull(mf)) {
				int iboard = dto.getIboard();
				String uploadPath = fileUtil.fileUpload(mf, IMG_SUFFIX_PATH);
				
				ThumbnailInsDto thumbnailInsDto = new ThumbnailInsDto();
				
				thumbnailInsDto.setIboard(iboard);
				thumbnailInsDto.setName(uploadPath);
				
				int thumbnailInsRows = mapper.thumbnailIns(thumbnailInsDto);
				
				if(!AssertUtil.notNull(thumbnailInsRows)) {
					throw new SQLException();
				}
			}
		} else {
			throw new SQLException();
		}
	}
	
	@Transactional
	public int adminBoardUpd(MultipartFile mf, AdminBoardUpdDto dto) throws IOException, SQLException {
		int iadmin = iadminGet();
		String menu = dto.getMenu();
		String icode = Menu.fromUrl(menu).CODE;
		String secYn = dto.getSecYn() == "true" ? "N" : "Y";
		
		dto.setIcode(icode);
		dto.setIadmin(iadmin);
		dto.setSecYn(secYn);

		int adminBoardUpdRows = mapper.adminBoardUpd(dto);

		if(AssertUtil.notNull(adminBoardUpdRows)) {
			if(AssertUtil.notNull(mf)) {
				int iboard = dto.getIboard();
				String uploadPath = fileUtil.fileUpload(mf, IMG_SUFFIX_PATH);
				
				ThumbnailUpdDto thumbnailUpdDto = new ThumbnailUpdDto();
				
				thumbnailUpdDto.setIboard(iboard);
				thumbnailUpdDto.setName(uploadPath);
				
				int thumbnailInsRows = mapper.thumbnailUpd(thumbnailUpdDto);
				
				if(!AssertUtil.notNull(thumbnailInsRows)) {
					throw new SQLException();
				}
			}
		} else {
			throw new SQLException();
		}
		
		return adminBoardUpdRows;
	}
	
	@Transactional
	public int adminBoardDel(int iboard) throws SQLException {
		int boardDelRows = mapper.adminBoardDel(iboard);

		if(!AssertUtil.notNull(boardDelRows)) {
			throw new SQLException();
		}
		
		return boardDelRows;
	}
	
	public List<MenuCodeGetVo> menuCodeGet() {
		return mapper.menuCodeGet();
	}
	
	public int adminBoardCntGet(AdminBoardCntGetDto dto) {
		return mapper.adminBoardCntGet(dto);
	}
	
	private int iadminGet() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		MyUserDetails userDetails = (MyUserDetails) principal;
		int iadmin = userDetails.getIadmin();
		
		return iadmin;
	}
}