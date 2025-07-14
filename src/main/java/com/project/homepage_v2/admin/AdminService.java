package com.project.homepage_v2.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.homepage_v2.admin.dto.AdminBoardCntGetDto;
import com.project.homepage_v2.admin.dto.AdminBoardGetDto;
import com.project.homepage_v2.admin.vo.AdminBoardGetVo;
import com.project.homepage_v2.admin.vo.MenuCodeGetVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper mapper;
	
	public List<AdminBoardGetVo> adminBoardGet(AdminBoardGetDto dto) {
		return mapper.adminBoardGet(dto);
	}
	
	public List<MenuCodeGetVo> menuCodeGet() {
		return mapper.menuCodeGet();
	}
	
	public int adminBoardCntGet(AdminBoardCntGetDto dto) {
		return mapper.adminBoardCntGet(dto);
	}
}