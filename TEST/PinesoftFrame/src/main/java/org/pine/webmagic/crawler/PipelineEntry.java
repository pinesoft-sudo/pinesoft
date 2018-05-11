/**
 * Project Name:WebMagic
 * File Name:PipelineEntry.java
 * Package Name:com.gisquest.webmagic.crawler
 * Date:2017�?3�?1�?
 * Copyright (c) 2017, Yangs All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.webmagic.crawler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.entity.MagicProcessor;
import org.pine.webmagic.entity.MagicRequest;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
/**
 * 
 * @author yangs
 *
 */
public class PipelineEntry {

	public static final Map<String, Spider> spiders = new HashMap<String, Spider>();

	@SuppressWarnings("deprecation")
	public static void ExcludeCrawler(MagicRequest mr) throws Exception {
		if (mr == null) {
			throw new Exception("MagicRequest");
		}
		else {
			MagicProcessor mp = mr.getProcessor();
			if (mp == null) {
				throw new Exception("MagicProcessor");
			}
			else {
				PageProcessorUtil ppu = new PageProcessorUtil(mp);
				Spider spider = Spider.create(ppu);

				if (mr.getEntryUrl() == null) {
					throw new Exception("MagicRequest.entryUrl");
				}
				else {
					spider = spider.addUrl(mr.getEntryUrl());
				}

				if (mr.getPipeline() != null && mr.getPipeline().size() > 0) {
					// 添加默认的结果查�?
					for (MagicEntrySet magicEntrySet : mr.getPipeline()) {
						// for (Map.Entry<String, String> entry : mr.getPipeline().entrySet()) {
						String sign = magicEntrySet.getKey().toLowerCase();
						String value = magicEntrySet.getValue() == null ? null : magicEntrySet.getValue().toString();
						switch (sign) {
							case "json" :
								if (StringUtils.isEmpty(value)) {
									spider = spider.addPipeline(new JsonFilePipeline());
								}
								else {
									spider = spider.addPipeline(new JsonFilePipeline(value));
								}
								break;
							case "file" :
								if (StringUtils.isEmpty(value)) {
									spider = spider.addPipeline(new FilePipeline());
								}
								else {
									spider = spider.addPipeline(new FilePipeline(value));
								}
								break;
							case "console" :
								spider = spider.addPipeline(new ConsolePipeline());
								break;
							case "oracle" :
								// 用来扩展 自定义pipeline
								spider = spider.addPipeline(new OraclePipeline(mr.getGuid()));
								break;
							default :
								spider = spider.addPipeline(new ConsolePipeline());
								break;
						}
					}
				}
				if (mr.getThreadNum() > 0) {
					spider = spider.thread(mr.getThreadNum());
				}

				// jxm监控存在问题，后期由版本进行更新
				if (mr.isMonitor() > 0) {
					MonitorCrawler(mr.getGuid(), spider);
				}

				if (spiders.containsKey(mr.getGuid())) {
					CloseCrawler(mr.getGuid());
				}
				spiders.put(mr.getGuid(), spider);

				if (mr.isAsync() > 0) {
					spider.start();
				}
				else {
					spider.run();
				}
			}
		}
	}

	@Deprecated
	private static void MonitorCrawler(String key, Spider spider) throws Exception {
		if (!spiders.get(key).equals(spider)) {
			SpiderMonitor.instance().register(spider);
		}
	}

	public static void RunCrawler(String key) throws Exception {
		if (spiders.get(key) == null)
			throw new Exception("Can not find instance in static spiders");
		else
			spiders.get(key).run();
	}

	public static void AsyncRunCrawler(String key) throws Exception {
		if (spiders.get(key) == null)
			throw new Exception("Can not find instance in static spiders");
		else
			spiders.get(key).start();
	}

	public static void StopCrawler(String key) throws Exception {
		if (spiders.get(key) == null)
			throw new Exception("Can not find instance in static spiders");
		else
			spiders.get(key).stop();
	}

	public static void CloseCrawler(String key) throws Exception {
		if (spiders.get(key) == null)
			throw new Exception("Can not find instance in static spiders");
		else {
			spiders.get(key).stop();
			spiders.get(key).close();
			spiders.remove(key);
		}
	}

}
