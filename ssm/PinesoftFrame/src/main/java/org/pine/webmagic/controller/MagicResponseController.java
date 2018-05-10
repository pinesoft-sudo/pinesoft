package org.pine.webmagic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.pine.webmagic.entity.MagicResponse;
import org.pine.webmagic.service.MagicResponseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "magic/response/")
public class MagicResponseController {
	@Resource
	private MagicResponseService magicResponseService;

	@RequestMapping(value = "get")
	public MessageInfo get(@RequestParam("guid") String guid) {
		try {
			MagicResponse response = magicResponseService.get(guid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取响应数据", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "getListByRequestGuid")
	public MessageInfo getListByRequestGuid(@RequestParam("guid") String guid) {
		try {
			List<MagicResponse> response = magicResponseService.getListByRequestGuid(guid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫响应数据", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "getCountByRequestGuid")
	public MessageInfo getCountByRequestGuid(@RequestParam("guid") String guid) {
		try {
			long response = magicResponseService.getCountByRequestGuid(guid);

			return new MessageInfo(ResultEnums.Success, "获取爬虫响应数据列总数", String.valueOf(response));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteListByRequestGuid")
	public MessageInfo deleteListByRequestGuid(@RequestParam("ids") String ids) {
		try {
			long response = magicResponseService.deleteListByRequestGuid(ids);

			return new MessageInfo(ResultEnums.Success, "删除爬虫响应数据", String.valueOf(response));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "clear")
	public MessageInfo clear() {
		try {
			long response = magicResponseService.clear();

			return new MessageInfo(ResultEnums.Success, "清空所有爬虫响应数据", String.valueOf(response));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}
}
