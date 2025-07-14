package com.project.homepage_v2.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminBoardGetDto {
	private String icode;
	private int offset;
	private int amount;
}