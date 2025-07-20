package com.project.homepage_v2.api;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	private ApiStatus apiStatus;

	public ApiException(ApiStatus apiStatus) {
		this.apiStatus = apiStatus;
	}
}