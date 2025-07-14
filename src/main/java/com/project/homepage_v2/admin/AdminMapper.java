package com.project.homepage_v2.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.homepage_v2.admin.dto.AdminBoardCntGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardGetDto;
import com.project.homepage_v2.admin.vo.AdminBoardGetVo;
import com.project.homepage_v2.admin.vo.MenuCodeGetVo;

@Mapper
public interface AdminMapper {
	List<AdminBoardGetVo> adminBoardGet(AdminBoardGetDto dto);
	
	// 게시판 메뉴
	List<MenuCodeGetVo> menuCodeGet();
	
	// 페이지네이션
	int adminBoardCntGet(AdminBoardCntGetDto dto);
}