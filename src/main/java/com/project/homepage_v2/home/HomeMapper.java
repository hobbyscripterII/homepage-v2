package com.project.homepage_v2.home;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.homepage_v2.home.vo.LatestPostGetVo;

@Mapper
public interface HomeMapper {
	List<LatestPostGetVo> latestPostGet();
}