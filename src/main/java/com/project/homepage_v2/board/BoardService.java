package com.project.homepage_v2.board;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.homepage_v2.board.dto.BoardGetDto;
import com.project.homepage_v2.board.vo.BoardGetVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper mapper;
	
	public List<BoardGetVo> boardGet(BoardGetDto dto) {
		return mapper.boardGet(dto);
	}
}