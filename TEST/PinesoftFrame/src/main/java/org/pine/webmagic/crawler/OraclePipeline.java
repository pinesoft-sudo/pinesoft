package org.pine.webmagic.crawler;

import java.util.UUID;

import org.pine.common.util.SpringContextUtil;
import org.pine.webmagic.entity.MagicResponse;
import org.pine.webmagic.service.MagicResponseService;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class OraclePipeline implements Pipeline {
	private String requestGuid;
	public OraclePipeline(String guid) {
		requestGuid = guid;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {

		MagicResponse mrp = new MagicResponse();
		String url = resultItems.getRequest().getUrl();
		String data = JSON.toJSONString(resultItems.getAll());
		mrp.setGuid(UUID.randomUUID().toString());
		mrp.setPageUrl(url);
		mrp.setRequestGuid(requestGuid);
		mrp.setValue(data);

		// 保存mrp到数据库
		try {
			MagicResponseService service = SpringContextUtil.getBean(MagicResponseService.class);
			service.insert(mrp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
