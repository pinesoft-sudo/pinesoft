/**
 * Project Name:WebMagic
 * File Name:PageProcessorUtil.java
 * Package Name:com.gisquest.webmagic.crawler
 * Date:2017�?3�?1�?
 * Copyright (c) 2017, Yangs All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.webmagic.crawler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pine.webmagic.entity.MagicEntrySet;
import org.pine.webmagic.entity.MagicProcessor;
import org.pine.webmagic.entity.MagicSelectItem;
import org.pine.webmagic.entity.MagicSelectable;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 
 * @author yangs
 *
 */
public class PageProcessorUtil implements PageProcessor {

	private Site site = Site.me();
	private MagicSelectable select;

	public PageProcessorUtil(MagicProcessor processor) {
		select = processor.getSelects();
		int retryTimes = processor.getRetryTimes();
		if (retryTimes > 0) {
			site = site.setRetryTimes(retryTimes);
		}

		String charset = processor.getCharset();
		if (StringUtils.isNotEmpty(charset)) {
			site = site.setCharset(charset);
		}

		int timeOut = processor.getTimeOut();
		if (timeOut > 0) {
			site = site.setTimeOut(timeOut);
		}

		int cycleRetryTimes = processor.getCycleRetryTimes();
		if (cycleRetryTimes > 0) {
			site = site.setTimeOut(timeOut);
		}

		int sleepTime = processor.getSleepTime();
		if (sleepTime > 0) {
			site = site.setSleepTime(sleepTime);
		}

		int retrySleepTime = processor.getRetrySleepTime();
		if (retrySleepTime > 0) {
			site = site.setRetrySleepTime(retrySleepTime);
		}

		// Map<String, String> addHeader = processor.getAddHeader();
		List<MagicEntrySet> addHeader = processor.getAddHeader();
		if (addHeader != null && addHeader.size() > 0) {
			// for (Map.Entry<String, String> entry : addHeader.entrySet()) {
			// site = site.addHeader(entry.getKey(), entry.getValue());
			// }
			for (MagicEntrySet magicEntrySet : addHeader) {
				site = site.addHeader(magicEntrySet.getKey(), magicEntrySet.getValue().toString());
			}
		}

		// Map<String, String> httpProxy = processor.getHttpProxy();
		List<MagicEntrySet> httpProxy = processor.getHttpProxy();
		if (httpProxy != null && httpProxy.size() > 0) {
			HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
			// for (Map.Entry<String, String> entry : httpProxy.entrySet()) {
			// Proxy P = new Proxy(entry.getKey(), Integer.valueOf(entry.getValue()));
			// httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(P));
			// }
			for (MagicEntrySet magicEntrySet : httpProxy) {
				Proxy P = new Proxy(magicEntrySet.getKey(), (Integer) (magicEntrySet.getValue()));
				httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(P));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		try {
			if (select == null) {
				throw new Exception("MagicSelectable is null");
			}
			else {
				List<MagicSelectItem> targets = select.getTargetRequests();
				if (targets != null && targets.size() > 0) {
					List<String> targetList = null;
					Selectable sa_html = page.getHtml();
					for (MagicSelectItem selectItem : targets) {
						targetList = (List<String>) SelectItemParse(page, sa_html, selectItem);
					}
					page.addTargetRequests(targetList);
				}

				List<MagicSelectItem> urls = select.getGetUrls();
				if (urls != null && urls.size() > 0) {
					Selectable sa_url = page.getUrl();
					for (MagicSelectItem selectItem : urls) {
						page.putField(selectItem.getName(), SelectItemParse(page, sa_url, selectItem));
					}
				}

				List<MagicSelectItem> htmls = select.getGetHtmls();
				if (htmls != null && htmls.size() > 0) {
					Selectable sa_html = page.getHtml();
					for (MagicSelectItem selectItem : htmls) {
						page.putField(selectItem.getName(), SelectItemParse(page, sa_html, selectItem));
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	@SuppressWarnings("rawtypes")
	private Object SelectItemParse(Page page, Selectable sa, MagicSelectItem si) throws Exception {
		// LinkedHashMap<String, Object> selects = si.getSelects();
		// for (Map.Entry<String, Object> entry : selects.entrySet()) {
		// sa = SelectTypeParse(sa, entry);
		// }
		List<MagicEntrySet> selects = si.getSelects();
		for (MagicEntrySet magicEntrySet : selects) {
			sa = SelectTypeParse(sa, magicEntrySet);
		}

		boolean isSkip = si.isSkip() > 0 ? true : false;
		String retType = si.getResultType();
		Object ret = null;
		switch (retType) {
			case "get" :
				ret = sa.get();
			case "toString" :
				ret = sa.toString();
				break;
			case "target" :
			case "all" :
				ret = sa.all();
				break;
			case "match" :
				ret = sa.match();
			default :
				ret = sa.all();
				break;
		}
		if (isSkip) {
			if (ret == null) {
				page.setSkip(true);
			}
			else {
				if ((ret.getClass() == ArrayList.class) && ((List) ret).size() <= 0) {
					page.setSkip(true);
				}
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private Selectable SelectTypeParse(Selectable sa, MagicEntrySet magicEntrySet) {
		String type = magicEntrySet.getKey();
		Object select = magicEntrySet.getValue();
		switch (type) {
			case "css" :
			case "$" : {
				SimpleEntry<String, String> css = (SimpleEntry<String, String>) select;
				String selector = css.getKey();
				String attr = css.getValue();
				if (StringUtils.isEmpty(attr)) {
					sa = sa.css(selector);
				}
				else {
					sa = sa.css(selector, attr);
				}
				break;
			}
			case "xpath" :
				String xpath = select.toString();
				sa = sa.xpath(xpath);
				break;
			case "links" :
				boolean links = Boolean.valueOf(select.toString());
				if (links) {
					sa = sa.links();
				}
				break;
			case "regex" :
				String regex = select.toString();
				sa = sa.regex(regex);
				break;
			case "jsonpath" :
				String json = select.toString();
				sa = sa.jsonPath(json);
				break;
			case "replace" :
				SimpleEntry<String, String> replace = (SimpleEntry<String, String>) select;
				String key = replace.getKey();
				String value = StringUtils.isEmpty(replace.getValue()) ? "" : replace.getValue();
				sa = sa.replace(key, value);
				break;
			default :
				break;
		}
		return sa;
	}
}
