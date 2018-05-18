package org.pine.mvc.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gisquest.MvcResponse;
import com.gisquest.MvcResponse.ResultEnums;

/**
 * @author xier:
 * @Description:
 * @date 创建时间：2018年2月8日 上午9:56:55
 * @version 2.0
 * @parameter
 * @since
 * @return
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	private Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public MvcResponse handleException(Exception e) {
		MvcResponse response = new MvcResponse();
		String msg = e.getMessage();
		if (msg == null || msg.equals("")) {
			msg = "服务器报错！";
		}
		response.setMessage(msg);
		response.setResult(ResultEnums.Error);
		logger.error(e.toString());
		return response;
	}

}
