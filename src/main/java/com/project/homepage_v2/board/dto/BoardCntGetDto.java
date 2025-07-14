package com.project.homepage_v2.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardCntGetDto {
	private String icode;
	private String role;
}