package com.project.homepage_v2.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.homepage_v2.board.dto.BoardGetDto;
import com.project.homepage_v2.board.vo.BoardGetVo;

@Mapper
public interface BoardMapper {
	List<BoardGetVo> boardGet(BoardGetDto dto);
}