package org.pine.mvc.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author xier:
 * @Description:
 * @date 创建时间：2018年3月2日 上午11:11:01
 * @version 2.0
 * @parameter
 * @since
 * @return
 */
@Controller
public class BaseController {
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
