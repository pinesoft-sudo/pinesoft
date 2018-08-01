package org.pine.mvc;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.pine.mvc.MvcResponse.ResultEnums;

//@ControllerAdvice + @ExceptionHandler 全局处理 Controller 层异常
@ControllerAdvice
public class MvcExceptionAdvice {
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public MvcResponse handleException(Exception e) {
		MvcResponse response = new MvcResponse();
		String msg = e.getMessage();
		if (msg == null || msg.equals("")) {
			msg = "service error !";
		}
		response.setMessage(msg);
		response.setResult(ResultEnums.Error);
		return response;
	}
}
