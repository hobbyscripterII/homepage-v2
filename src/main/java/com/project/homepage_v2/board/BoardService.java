package com.project.homepage_v2.board;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.homepage_v2.board.dto.BoardCntGetDto;
import com.project.homepage_v2.board.dto.BoardGetDto;
import com.project.homepage_v2.board.vo.BoardGetVo;
import com.project.homepage_v2.board.vo.BoardSelVo;
import com.project.homepage_v2.board.vo.NextPostGetVo;
import com.project.homepage_v2.board.vo.PrevPostGetVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper mapper;
	
	public List<BoardGetVo> boardGet(BoardGetDto dto) {
		return mapper.boardGet(dto);
	}
	
	public BoardSelVo boardSel(int iboard) {
		// 예외 처리 필요(매칭되는 게시글이 없거나 삭제된 게시글 등)
		return mapper.boardSel(iboard);
	}
	
	public PrevPostGetVo prevPostGet(int iboard) {
		return mapper.prevPostGet(iboard);
	}
	
	public NextPostGetVo nextPostGet(int iboard) {
		return mapper.nextPostGet(iboard);
	}
	
	public int boardCntGet(BoardCntGetDto dto) {
		return mapper.boardCntGet(dto);
	}
}