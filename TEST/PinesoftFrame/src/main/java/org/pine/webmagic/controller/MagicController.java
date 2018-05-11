/**
 * Project Name:WebMagic
 * File Name:MagicController.java
 * Package Name:com.gisquest.webmagic.entity
 * Date:2017�?3�?1�?
 * Copyright (c) 2017, Yangs All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.webmagic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.pine.webmagic.crawler.PipelineEntry;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.entity.MagicProcessor;
import org.pine.webmagic.entity.MagicRequest;
import org.pine.webmagic.entity.MagicSelectItem;
import org.pine.webmagic.entity.MagicSelectable;
import org.pine.webmagic.service.MagicRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author yangs
 *
 */
@RestController
@RequestMapping(value = "magic/")
public class MagicController {
	@Resource
	private MagicRequestService magicRequestService;

	@RequestMapping(value = "index")
	public ModelAndView MagicIndex() throws Exception {
		ModelAndView mav = new ModelAndView("magicIndex");
		return mav;
	}

	@RequestMapping(value = "test")
	public MessageInfo MagicTest() {
		try {
			MagicRequest mr = new MagicRequest();
			mr.setGuid("111111111111111111");
			mr.setAsync(1);
			mr.setEntryUrl("https://github.com/code4craft");
			// mr.setMonitor(1);
			mr.setThreadNum(5);

			List<MagicEntrySet> pipelines = new ArrayList<MagicEntrySet>();
			MagicEntrySet mes_pipe = new MagicEntrySet();
			mes_pipe.setKey("console");
			pipelines.add(mes_pipe);
			// mes_pipe = new MagicEntrySet();
			// mes_pipe.setKey("oracle");
			// pipelines.add(mes_pipe);
			// mes_pipe = new MagicEntrySet();
			// mes_pipe.setKey("file");
			// mes_pipe.setValue("D:\\webmagic_f\\");
			// pipelines.add(mes_pipe);
			mr.setPipeline(pipelines);

			MagicProcessor mp = new MagicProcessor();
			mp.setRetryTimes(3);
			mp.setSleepTime(1000);
			mp.setTimeOut(10000);
			mp.setCharset("utf-8");

			MagicSelectable ms = new MagicSelectable();

			List<MagicSelectItem> urls = new ArrayList<MagicSelectItem>();
			MagicSelectItem s = new MagicSelectItem();
			s.setName("author");
			s.setResultType("toString");
			s.setSkip(1);
			// LinkedHashMap<String, Object> select_url = new LinkedHashMap<String, Object>();
			// select_url.put("regex", "https://github\\.com/(\\w+)/.*");
			List<MagicEntrySet> select_url = new ArrayList<MagicEntrySet>();
			MagicEntrySet mes = new MagicEntrySet();
			mes.setKey("regex");
			mes.setValue("https://github\\.com/(\\w+)/.*");
			select_url.add(mes);
			s.setSelects(select_url);
			urls.add(s);

			List<MagicSelectItem> htmls = new ArrayList<MagicSelectItem>();
			MagicSelectItem s1 = new MagicSelectItem();
			s1.setName("name");
			s1.setResultType("all");
			s1.setSkip(1);
			// LinkedHashMap<String, Object> select_html = new LinkedHashMap<String, Object>();
			// select_html.put("xpath", "//h1[@class='public']/strong/a/text()");
			List<MagicEntrySet> select_html = new ArrayList<MagicEntrySet>();
			MagicEntrySet mes1 = new MagicEntrySet();
			mes1.setKey("xpath");
			mes1.setValue("//h1[@class='public']/strong/a/text()");
			select_html.add(mes1);
			s1.setSelects(select_html);
			htmls.add(s1);

			List<MagicSelectItem> targets = new ArrayList<MagicSelectItem>();
			MagicSelectItem s2 = new MagicSelectItem();
			s2.setResultType("all");
			// LinkedHashMap<String, Object> select_targets = new LinkedHashMap<String, Object>();
			// select_targets.put("links", true);
			// select_targets.put("regex", "(https://github\\.com/[\\w\\-]+/[\\w\\-]+)");
			List<MagicEntrySet> select_targets = new ArrayList<MagicEntrySet>();
			MagicEntrySet mes2 = new MagicEntrySet();
			mes2.setKey("links");
			mes2.setValue(true);
			MagicEntrySet mes3 = new MagicEntrySet();
			mes3.setKey("regex");
			mes3.setValue("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)");
			select_targets.add(mes2);
			select_targets.add(mes3);
			s2.setSelects(select_targets);
			targets.add(s2);

			ms.setGetUrls(urls);
			ms.setGetHtmls(htmls);
			ms.setTargetRequests(targets);

			mp.setSelects(ms);
			mr.setProcessor(mp);
			
			
			PipelineEntry.ExcludeCrawler(mr);
			return new MessageInfo(ResultEnums.Success, "开始测试");
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "start")
	public MessageInfo MagicStart(@RequestParam(value = "guid", required = false) String guid) {
		try {
			MagicRequest request;
			if (!StringUtils.isEmpty(guid)) {
				request = magicRequestService.get(guid);
			}
			// else if (!StringUtil.isNullOrEmpty(record)) {
			// request = JsonUtil.jsonToPojo(record, MagicRequest.class);
			// }
			else {
				throw new Exception("未获取到有效的请求信息");
			}
			PipelineEntry.ExcludeCrawler(request);
			return new MessageInfo(ResultEnums.Success, "开始执行");
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "close")
	public MessageInfo MagicClose(@RequestParam("guid") String guid) {
		try {
			PipelineEntry.CloseCrawler(guid);
			return new MessageInfo(ResultEnums.Success, "已关闭");
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "run")
	public MessageInfo MagicRun(@RequestParam("guid") String guid) {
		try {
			PipelineEntry.RunCrawler(guid);
			return new MessageInfo(ResultEnums.Success, "已开启");
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "asyncrun")
	public MessageInfo MagicAsyncRun(@RequestParam("guid") String guid) {
		try {
			PipelineEntry.AsyncRunCrawler(guid);
			return new MessageInfo(ResultEnums.Success, "已异步开启");
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}

	@RequestMapping(value = "stop")
	public MessageInfo MagicStop(@RequestParam("guid") String guid) {
		try {
			PipelineEntry.StopCrawler(guid);
			return new MessageInfo(ResultEnums.Success, "已停止");
		}
		catch (Exception ex) {
			return new MessageInfo(ResultEnums.Error, ex.getMessage());
		}
	}
}
