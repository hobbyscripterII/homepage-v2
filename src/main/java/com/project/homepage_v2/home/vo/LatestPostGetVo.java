package com.project.homepage_v2.home.vo;

import lombok.Data;

@Data
public class LatestPostGetVo {
	private int iboard;
	private String icode;
	private String title;
	private String createdAt;
	private String boardUrl;
	private String boardName;
}
