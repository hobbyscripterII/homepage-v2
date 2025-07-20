package com.project.homepage_v2.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
	private int status;
	private String message;
	
	@Builder.Default // builder 패턴으로 객체 생성 시 기본 값 지정
	private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	private T data;
	private String errors;
	
	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.<T>builder()
				.status(ApiStatus.SUCCESS.getStatusCode())
				.message(ApiStatus.SUCCESS.getMessage())
				.data(data)
				.build();
	}
	
	public static <T> ApiResponse<T> error(int status, String message, String errors) {
		return ApiResponse.<T>builder()
				.status(status)
				.message(message)
				.errors(errors)
				.build();
	}
}