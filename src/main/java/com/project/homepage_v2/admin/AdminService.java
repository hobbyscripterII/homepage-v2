package com.project.homepage_v2.admin;

import static com.project.homepage_v2.cmmn.Const.IMG_SUFFIX_PATH;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import com.project.homepage_v2.admin.vo.AdminBoardContentsGetAllVo;
import com.project.homepage_v2.admin.vo.AdminBoardGetVo;
import com.project.homepage_v2.admin.vo.AdminThumbnailNameGetAllVo;
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
		String secYn = dto.getSecYn() == "false" ? "N" : "Y";
		
		dto.setIcode(icode);
		dto.setIadmin(iadmin);
		dto.setSecYn(secYn);

		int adminBoardInsRows = mapper.adminBoardIns(dto);

		if(AssertUtil.notNull(adminBoardInsRows)) {
			if(AssertUtil.notNull(mf)) {
				int iboard = dto.getIboard();
				String uploadPath = fileUtil.uploadFile(mf, IMG_SUFFIX_PATH);
				
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
		String secYn = dto.getSecYn() == "true" ? "Y" : "N";
		
		dto.setIcode(icode);
		dto.setIadmin(iadmin);
		dto.setSecYn(secYn);

		int adminBoardUpdRows = mapper.adminBoardUpd(dto);

		if(AssertUtil.notNull(adminBoardUpdRows)) {
			if(AssertUtil.notNull(mf)) {
				int iboard = dto.getIboard();
				String uploadPath = fileUtil.uploadFile(mf, IMG_SUFFIX_PATH);
				
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
	
	@Transactional
	public void adminBoardRemoveFileTask() throws IOException {
		// 게시글 찾기
		List<AdminBoardContentsGetAllVo> adminBoardContentsGetAll = mapper.adminBoardContentsGetAll();
		// 썸네일 찾기
		List<AdminThumbnailNameGetAllVo> adminThumbnailNameGetAll = mapper.adminThumbnailNameGetAll();
		// 게시판 이미지 담을 리스트
		List<String> imgList = new ArrayList<String>();
		// 썸네일 이미지 담을 셋
		Set<String> fileNameList = new HashSet<String>();
		String imgTag = "<img";
		
		// img 태그가 있는 게시글만 추출
		for(AdminBoardContentsGetAllVo vo : adminBoardContentsGetAll) {
			String contents = vo.getContents();
			
			if(AssertUtil.notNull(contents) && contents.contains(imgTag)) {
				imgList.add(contents);
			}
		}
		
		// img 태그의 src 속성 값만 추출
		List<String> imgSrcList = new ArrayList<String>();
		String regex = "<img[^>]+src=[\"'](/img/[^\"'>]+)[\"']";
		Pattern pattern = Pattern.compile(regex);
		
		for(String img : imgList) {
			Matcher matcher = pattern.matcher(img);
			
			while(matcher.find()) {
				imgSrcList.add(matcher.group(1));
			}
		}
		
		// 이미지 경로 바인딩 path 제거한 uuid 파일명 + 확장자만 추출
		fileNameList.addAll(
			imgSrcList.stream()
			.map(path -> path.substring(path.lastIndexOf("/") + 1))
			.collect(Collectors.toSet())
		);
		
		fileNameList.addAll(
			adminThumbnailNameGetAll.stream()
			.map(name -> name.getName().substring(name.getName().lastIndexOf("/") + 1))
			.collect(Collectors.toSet())
		);
		
		// IOException 발생 시 Controller 쪽으로 예외 던지기
		fileUtil.deleteFiles(fileNameList);
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