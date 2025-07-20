package com.project.homepage_v2.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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

@Mapper
public interface AdminMapper {
	List<AdminBoardGetVo> adminBoardGet(AdminBoardGetDto dto);
	BoardSelVo adminBoardSel(int iboard);
	
	int adminBoardIns(AdminBoardInsDto dto);
	int adminBoardDel(int iboard);
	int adminBoardUpd(AdminBoardUpdDto dto);
	
	// 게시판 메뉴
	List<MenuCodeGetVo> menuCodeGet();
	
	// 페이지네이션
	int adminBoardCntGet(AdminBoardCntGetDto dto);
	
	// 썸네일
	int thumbnailIns(ThumbnailInsDto dto);
	int thumbnailUpd(ThumbnailUpdDto dto);
	
	// 불필요 파일 제거
	List<AdminBoardContentsGetAllVo> adminBoardContentsGetAll();
	List<AdminThumbnailNameGetAllVo> adminThumbnailNameGetAll();
}