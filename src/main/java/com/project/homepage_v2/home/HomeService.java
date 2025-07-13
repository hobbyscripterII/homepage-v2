package com.project.homepage_v2.home;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.homepage_v2.home.vo.LatestPostGetVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	private final HomeMapper mapper;
	
	public List<LatestPostGetVo> latestPostGet() {
		return mapper.latestPostGet();
	}
}