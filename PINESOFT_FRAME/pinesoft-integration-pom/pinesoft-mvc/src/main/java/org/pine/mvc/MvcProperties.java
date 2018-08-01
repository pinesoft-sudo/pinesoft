package org.pine.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MvcProperties {
	@Value("${gisquest.common.staticPath:}")
	private String commonStaticPath;
	@Value("${gisquest.staticPath:}")
	private String staticPath;

	@ModelAttribute
	public void init(Model model) {
		model.addAttribute("commonStaticPath", commonStaticPath);
		model.addAttribute("staticPath", staticPath);
	}
}
