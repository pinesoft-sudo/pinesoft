/**
 * Project Name:QueryList
 * File Name:BaseController.java
 * Package Name:com.gisquest.querylist.controller
 * Date:2017年3月7日
 * Copyright (c) 2017, Gisquest All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.pine.boot.config.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: yangs
 * @Class: BaseController
 * @Date: 2017年3月7日
 * @version: v1.0
 * @Function: ADD FUNCTION.
 * @Reason: ADD REASON.
 * @since JDK 1.8
 */

//引入自己的properties
@PropertySource("classpath:config/custom.properties") 
public class BaseController {
	// 录制文件保存地址
	  @Value("${pine.filepath}")
	public String savepath;

	// // 获取直播用户服务
	@Value("${pine.liveuser}")
	public String liveuser;

	// 自定义配置对象(通过注入参数对象来获取)
	@Autowired
	private CustomConfig conf;

	/*
	 * //执行调度任务
	 * 
	 * @Scheduled(fixedRate = 5000)//上一次开始执行时间点之后5秒再执行
	 * 
	 * @Scheduled(fixedDelay = 5000) //上一次执行完毕时间点之后5秒再执行
	 * 
	 * @Scheduled(initialDelay=1000, fixedRate=5000)
	 * //第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
	 * 
	 *   @Scheduled(cron=" /5 ") //通过cron表达式定义规则，什么是cro表达式，自行搜索引擎。
	 */
	@Scheduled(fixedRate = 500000)
	public void reportCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println("The time is now " + dateFormat.format(new Date()));
		System.out.println("local-value：" + savepath);
		System.out.println("class-value：" + conf.getFilepath());
	}
}
