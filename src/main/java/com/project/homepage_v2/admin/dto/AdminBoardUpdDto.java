package com.project.homepage_v2.admin.dto;

import lombok.Data;

@Data
public class AdminBoardUpdDto {
	private int iboard;
	private int iadmin;
	
	private String icode;
	private String title;
	private String contents;
	private String youtubeId;
	private String secYn;
	
	private String menu;
}