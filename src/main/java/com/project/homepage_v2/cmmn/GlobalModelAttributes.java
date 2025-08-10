package com.project.homepage_v2.cmmn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalModelAttributes {
	private String cssVersion;

	public GlobalModelAttributes(@Value("${css.version}") String cssVersion) {
		this.cssVersion = cssVersion;
	}

	@ModelAttribute("cssVersion")
	public String cssVersion(Model model) {
		if (!AssertUtil.notNull(cssVersion)) {
			String v1 = "v1";
			cssVersion = v1;
		}

		return cssVersion;
	}
}