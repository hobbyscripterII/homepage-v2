package com.project.homepage_v2.cmmn.enums;

import lombok.Getter;

@Getter
public enum Role {
	ROLE_PREFIX("ROLE_"),
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_ANONYMOUS("ROLE_ANONYMOUS");
	
	public final String NAME;
	
	Role(String name) {
		this.NAME = name;
	}
}