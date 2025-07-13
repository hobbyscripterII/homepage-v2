package com.project.homepage_v2.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.homepage_v2.board.dto.BoardGetDto;
import com.project.homepage_v2.board.vo.BoardCntGetDto;
import com.project.homepage_v2.board.vo.BoardGetVo;
import com.project.homepage_v2.board.vo.BoardSelVo;
import com.project.homepage_v2.board.vo.NextPostGetVo;
import com.project.homepage_v2.board.vo.PrevPostGetVo;

@Mapper
public interface BoardMapper {
	List<BoardGetVo> boardGet(BoardGetDto dto);
	BoardSelVo boardSel(int iboard);
	
	// 이전 글
	PrevPostGetVo prevPostGet(int iboard);
	// 다음 글
	NextPostGetVo nextPostGet(int iboard);
	
	// 페이지네이션
	int boardCntGet(BoardCntGetDto dto);
}