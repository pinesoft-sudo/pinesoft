package org.pine.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestController {

	@Value("${mybatis.mapper-locations}")
	public String url;
	
	@ModelAttribute
	public void init(Model model) {
		model.addAttribute("commonStaticPath", "123456");
		model.addAttribute("staticPath", "56789");
	}

	@RequestMapping(value = "/test/{aa}/comment")
	public ModelAndView index(@PathVariable String aa, RedirectAttributes attr, Model model) {
		ModelAndView mView = new ModelAndView("/admin/test");
		attr.addAttribute("test", "cc");
		model.addAttribute("test1", aa);
		model.addAttribute("test2", url);
		return mView;
	}
}
