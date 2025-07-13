package com.project.homepage_v2.security;

import static com.project.homepage_v2.cmmn.enums.Role.*;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	private String id;
	private String pwd;
	private String name;
	private String role;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX.NAME + role));
		
		return list;
	}
	public String getId() {
		return id;
	}
	@Override
	public String getPassword() {
		return pwd;
	}
	@Override
	public String getUsername() {
		return name;
	}
}
