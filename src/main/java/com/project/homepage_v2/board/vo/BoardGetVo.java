package com.project.homepage_v2.board.vo;

import lombok.Data;

@Data
public class BoardGetVo {
	private int iboard;
	private String icode;
	private String name;
	private String boardName;
	private String title;
	private String createdAt;
	private String secYn;
	private String thumbnail;
	private String youtubeId;
}