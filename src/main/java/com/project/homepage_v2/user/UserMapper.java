package com.project.homepage_v2.user;

import org.apache.ibatis.annotations.Mapper;

import com.project.homepage_v2.security.MyUserDetails;

@Mapper
public interface UserMapper {
	MyUserDetails userGet(String id);
}