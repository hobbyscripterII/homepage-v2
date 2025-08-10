package com.project.homepage_v2.cmmn.enums;

import lombok.Getter;

@Getter
public enum Menu {
	NOTICE("공지사항", "B001", "notice"),
	DESIGN("디자인", "B006", "design"),
	MUSIC("음악", "B004", "music"),
	PHOTO("사진", "B003", "photo"),
	REVIEW("리뷰", "B008", "review"),
	DAILY("일상", "B005", "daily");
	
	public final String NAME;
	public final String CODE;
	public final String URL;
	
	Menu(String name, String code, String url) {
		this.NAME = name;
		this.CODE = code;
		this.URL = url;
	}
	
	public static Menu fromUrl(String url) {
		for(Menu menu : Menu.values()) {
			if(menu.URL.equals(url)) {
				return menu;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	public static Menu fromCode(String code) {
		for(Menu menu : Menu.values()) {
			if(menu.CODE.equals(code)) {
				return menu;
			}
		}
		
		throw new IllegalArgumentException();
	}
}