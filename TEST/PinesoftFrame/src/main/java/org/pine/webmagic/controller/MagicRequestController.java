package org.pine.webmagic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.pine.webmagic.entity.MagicRequest;
import org.pine.webmagic.service.MagicRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;


@RestController
@RequestMapping(value = "magic/request/")
public class MagicRequestController {
	@Resource
	private MagicRequestService magicRequestService;

	@RequestMapping(value = "getList")
	public MessageInfo getList() {
		try {
			List<MagicRequest> response = magicRequestService.getList(null);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫请求对象列", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "get")
	public MessageInfo get(@RequestParam("guid") String guid) {
		try {
			MagicRequest response = magicRequestService.get(guid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取爬虫请求对象", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "add")
	public MessageInfo add(@RequestParam("record") String record) {
		try {
			MagicRequest request = JSON.parseObject(record, MagicRequest.class);
			String ret = magicRequestService.insert(request);
			return new MessageInfo(ResultEnums.Success, "新增爬虫请求对象", ret);
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "addList")
	public MessageInfo addList(@RequestParam("record") String record) {
		try {
			List<MagicRequest> request = JSON.parseArray(record, MagicRequest.class);
			int ret = magicRequestService.insertList(request);
			return new MessageInfo(ResultEnums.Success, "新增爬虫请求对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "deleteListByGuids")
	public MessageInfo deleteListByGuids(@RequestParam("ids") String ids) {
		try {
			int ret = magicRequestService.deleteList(ids);
			return new MessageInfo(ResultEnums.Success, "删除爬虫请求对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updatePortion")
	public MessageInfo updatePortion(@RequestParam("record") String record) {
		try {
			MagicRequest request = JSON.parseObject(record, MagicRequest.class);
			int ret = magicRequestService.updatePortion(request);
			return new MessageInfo(ResultEnums.Success, "更新（合并）爬虫请求对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "updateComplete")
	public MessageInfo updateComplete(@RequestParam("record") String record) {
		try {
			MagicRequest request = JSON.parseObject(record, MagicRequest.class);
			int ret = magicRequestService.updateComplete(request);
			return new MessageInfo(ResultEnums.Success, "更新（覆盖）爬虫请求对象[" + ret + "]项", String.valueOf(ret));
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

}
