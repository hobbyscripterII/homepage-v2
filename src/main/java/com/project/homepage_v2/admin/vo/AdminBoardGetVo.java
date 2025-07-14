package com.project.homepage_v2.admin.vo;

import lombok.Data;

@Data
public class AdminBoardGetVo {
	private int iboard;
	private String icode;
	private String boardName;
	private String title;
	private String secYn;
	private String delYn;
	private String createdAt;
}