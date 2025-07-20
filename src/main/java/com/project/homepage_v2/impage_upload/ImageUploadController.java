package com.project.homepage_v2.impage_upload;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.homepage_v2.cmmn.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageUploadController {
	private final FileUtil fileUtil;
	
	@PostMapping("/image_upload")
	public ModelAndView imageUpload(MultipartHttpServletRequest request) throws IOException {
		try {
			ModelAndView mv = new ModelAndView("jsonView");
			MultipartFile mf = request.getFile("upload");
			String path = fileUtil.uploadFile(mf, "/img/");
			
			mv.addObject("uploaded", true);
			mv.addObject("url", path);
			
			return mv;
		} catch(IOException e) {
			log.error("[예외 발생] 파일 업로드에 실패했습니다. {}", e.getMessage());
			
			return null;
		}
	}
}
