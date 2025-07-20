package com.project.homepage_v2.cmmn;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtil {
	private final String PREFIX_PATH;
	
	public FileUtil(@Value("${file.path}") String prefixPath) {
		this.PREFIX_PATH = prefixPath;
	}
	
	public String uploadFile(MultipartFile mf, String suffixPath) throws IOException {
		String fileName = uuid(mf);
		Path  path = Paths.get(PREFIX_PATH);
		
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch(IOException e) {
				throw new IOException(e);
			}
		}
		
		String savePath = Paths.get(PREFIX_PATH + fileName).toString(); // 로컬에 저장되는 경로
		String uploadPath = suffixPath + fileName; // WebConfig를 통해 연결되는 외부 리소스 경로
		File saveFile = new File(savePath);
		
		try {
			mf.transferTo(saveFile);
		} catch(IOException e) {
			throw new IOException(e);
		}
		
		return uploadPath;
	}
	
	public void deleteFile(String fileName) throws IOException {
		Path dir = Paths.get(PREFIX_PATH, fileName);
		
		// 해당 디렉토리가 없을 경우 return
		if(!Files.exists(dir)) {
			return;
		}
		
		try {
			Files.delete(dir);
			
			log.info("[파일 삭제 성공] {}", fileName);
		} catch (IOException e) {
			log.error("[파일 삭제 실패] {} {}", fileName, e);
		}
	}
	
	public void deleteFiles(Set<String> fileNameList) throws IOException {
		Path dir = Paths.get(PREFIX_PATH);
		
		// 해당 디렉토리가 없을 경우 return
		if(!Files.exists(dir)) {
			return;
		}
		
		Files.list(dir).forEach(file -> {
			String fileName = file.getFileName().toString();
			
			if(!fileNameList.contains(fileName)) {
				try {
					Files.delete(file);
					
					log.info("[파일 삭제 성공] {}", fileName);
				} catch (IOException e) {
					log.error("[파일 삭제 실패] {} {}", fileName, e);
				}
			}
		});
	}
	
	private String uuid(MultipartFile mf) {
		String originalName = mf.getOriginalFilename(); // 원래 파일명
		String ext = originalName.substring(originalName.lastIndexOf(".")); // 확장자
		String uuid = (String.valueOf(UUID.randomUUID())) + ext; // UUID + 확장자
		
		return uuid;
	}
}