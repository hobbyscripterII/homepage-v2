package com.project.homepage_v2.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardGetDto {
	private String icode;
	private int offset;
	private int amount;
}