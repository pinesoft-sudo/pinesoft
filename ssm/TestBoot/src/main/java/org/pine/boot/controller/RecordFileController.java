package org.pine.boot.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.pine.annotation.ControllerLog;
import org.pine.boot.entity.RecordFile;
import org.pine.boot.netty.IMEventHandler;
import org.pine.boot.service.RecordFileService;
import org.pine.common.util.FileUtil;
import org.pine.common.util.LogUtil;
import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * @author: yangs
 * @Class: RecordFileController
 * @Date: 2017年12月12日
 * @version: v1.0
 * @Function: ADD FUNCTION.
 * @Reason: ADD REASON.
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "pine/")
public class RecordFileController extends BaseController {

	@Autowired
	private RecordFileService recordFileService;
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "Index")
	public ModelAndView recordFileIndex(@RequestParam(value = "userId", required = false) String userId) {
		ModelAndView mav = new ModelAndView("index");
		try {
			mav.addObject("userId", userId);
			mav.addObject("liveuser", liveuser);
		} catch (Exception e) {
			mav.addObject("retinfo", new MessageInfo(ResultEnums.Error, "获取数据出现异常"));
		}
		return mav;
	}

	@RequestMapping(value = "getRecordFiles", method = RequestMethod.GET)
	@ControllerLog(description="test_@ControllerLog")
	// @Cacheable(cacheNames="countUser")
	public MessageInfo getRecordFiles() {
		try {
			List<RecordFile> rfList = recordFileService.getList(null);
			String jsonStr = JSON.toJSONString(rfList);

			// 测试 消费RESTFUL的服务
			String rest = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
			// 测试日志信息
			LogUtil.debug("this is debug test[{}]", "DEBUG");
			LogUtil.info("this is info test[{}]", "INFO");
			LogUtil.warn("this is warn test[{}]", "WARN");
			LogUtil.error("this is error test[{}]", "ERROR");

			return new MessageInfo(ResultEnums.Success, rest, jsonStr);
		} catch (Exception e) {
			return new MessageInfo(ResultEnums.Error, "获取数据出现异常");
		}
	}

	@RequestMapping(value = "getRecordFileFilter", method = RequestMethod.GET)
	public MessageInfo getRecordFileFilter(@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "date", required = false) String date) {
		try {
			if (StringUtils.isEmpty(path)) {
				path = savepath;
			}
			List<RecordFile> rfList = recordFileService.getRecordFileByIO(path, name, date);
			String jsonStr = JSON.toJSONString(rfList);
			return new MessageInfo(ResultEnums.Success, "获取录制文件信息（IO方式）", jsonStr);
		} catch (Exception e) {
			return new MessageInfo(ResultEnums.Error, "获取数据出现异常");
		}
	}

	@RequestMapping(value = "downLoadRecordFile", method = RequestMethod.GET)
	public void downLoadRecordFile(HttpServletResponse response, @RequestParam(value = "filename") String filename,
			@RequestParam(value = "path", required = false) String path) {
		String filePath = null;
		if (StringUtils.isEmpty(path)) {
			filePath = savepath + filename;
		} else {
			filePath = path + filename;
		}

		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			// FileUtil.download(filePath, response);
		}
	}

	@RequestMapping(value = "deleteRecordFile", method = RequestMethod.GET)
	public void deleteRecordFile(HttpServletResponse response, @RequestParam(value = "filename") String filename,
			@RequestParam(value = "path", required = false) String path) {
		String filePath = null;
		if (StringUtils.isEmpty(path)) {
			filePath = savepath + filename;
		} else {
			filePath = path + filename;
		}
		FileUtil.delFile(filePath);
	}

	@RequestMapping(value = "get")
	public MessageInfo get(@RequestParam("guid") String guid) {
		try {
			RecordFile response = recordFileService.get(guid);
			String ret = JSON.toJSONString(response);
			return new MessageInfo(ResultEnums.Success, "获取录制视频对象", ret);
		} catch (Exception ex) {
			String errorinfo = "获取所有数据异常，详细信息请查看日志";
			return new MessageInfo(ResultEnums.Error, errorinfo);
		}
	}

	@RequestMapping(value = "add")
	public MessageInfo add(@RequestParam("record") String record) {
		try {
			RecordFile request = JSON.parseObject(record, RecordFile.class);
			String ret = recordFileService.insert(request);
			return new MessageInfo(ResultEnums.Success, "新增录制视频对象", ret);
		} catch (Exception ex) {
			String errorinfo = "获取所有数据异常，详细信息请查看日志";
			return new MessageInfo(ResultEnums.Error, errorinfo);
		}
	}

	@RequestMapping(value = "update")
	public MessageInfo update(@RequestParam("record") String record) {
		try {
			RecordFile request = JSON.parseObject(record, RecordFile.class);
			int ret = recordFileService.updatePortion(request);
			return new MessageInfo(ResultEnums.Success, "更新录制视频对象", String.valueOf(ret));
		} catch (Exception ex) {
			String errorinfo = "获取所有数据异常，详细信息请查看日志";
			return new MessageInfo(ResultEnums.Error, errorinfo);
		}
	}

	@RequestMapping(value = "addList")
	public MessageInfo addList(@RequestParam("record") String record) {
		try {
			List<RecordFile> request = JSON.parseArray(record, RecordFile.class);
			int ret = recordFileService.insertList(request);
			return new MessageInfo(ResultEnums.Success, "新增录制视频对象[" + ret + "]列", String.valueOf(ret));
		} catch (Exception ex) {
			String errorinfo = "获取所有数据异常，详细信息请查看日志";
			return new MessageInfo(ResultEnums.Error, errorinfo);
		}
	}

	@RequestMapping(value = "delete")
	public MessageInfo delete(@RequestParam("guid") String guid, HttpServletResponse response,
			@RequestParam(value = "isDelFile") boolean isDelFile,
			@RequestParam(value = "path", required = false) String path) {
		try {
			int ret = recordFileService.delete(guid);
			if (ret > 0 && isDelFile) {
				RecordFile rf = recordFileService.get(guid);
				String filename = rf.getFilename();
				if (!StringUtils.isEmpty(filename)) {
					String filePath = null;
					if (StringUtils.isEmpty(path)) {
						filePath = savepath + filename;
					} else {
						filePath = path + filename;
					}
					FileUtil.delFile(filePath);
				}
			}
			return new MessageInfo(ResultEnums.Success, "删除录制视频对象[" + ret + "]列", String.valueOf(ret));
		} catch (Exception ex) {
			String errorinfo = "获取所有数据异常，详细信息请查看日志";
			return new MessageInfo(ResultEnums.Error, errorinfo);
		}
	}

	@RequestMapping(value = "netty_web")
	public void sWebMsg() {
		IMEventHandler.sendToWeb("服务端==>WEB的消息");
		System.out.println("netty 已推送信息到前端！");
	}
	
	@RequestMapping(value = "netty_client")
	public void sClientMsg() {
		try {
			IMEventHandler.sendToServer("[后端]客户端==>服务端的消息");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
