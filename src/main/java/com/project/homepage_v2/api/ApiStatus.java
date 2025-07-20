package com.project.homepage_v2.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiStatus {
	SUCCESS(200, "요청이 성공적으로 완료되었습니다."),
	CREATED(201, "리소스가 성공적으로 생성되었습니다."),
	BAD_REQUEST(400, "요청이 유효하지 않습니다."),
	UNAUTHORIZED(401, "권한이 확인되지 않았습니다."),
	FORBIDDEN(403, "접근이 거부되었습니다."),
	NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),
	REQUIRED_ERROR(422, "필수 값이 누락되었습니다."),
	INTERNAL_SERVER_ERROR(500, "서버에서 예기치 못한 오류가 발생했습니다.");
	
	private final int statusCode;
	private final String message;
}