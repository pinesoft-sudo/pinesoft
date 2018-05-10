/**  
 * Project Name:GisqDCServerRest  
 * File Name:SolrClientInstance.java  
 * Package Name: com.gisquest.solr.basis  
 * Date:2016年10月20日
 * Copyright (c) 2016, Gisquest All Rights Reserved.  
 * Current Author:  yangs.
*/
package org.pine.solr.basis;

/**
 * @author:    yangs  
 * @Class:      SolrClientInstance 
 * @Date:       2016年10月20日
 * @version:   v1.0
 * @Function:  ADD FUNCTION.
 * @Reason:    ADD REASON. 
 * @since       JDK 1.8       
 */
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.commons.lang3.StringUtils;

public class SolrClientInstance {

	/* solr服务地址 */
	public static String SOLR_URL;
	/* solr服务空间 */
	public static String SOLR_HOME;
	/* solr服务客户端实例 */
	public static SolrClient SOLR_CLIENT;
	/* solr服务空间地址 */
	public static String S_URL;

	/**
	 * 构造函数
	 * 
	 * @param solrurl
	 *            服务地址
	 * @param solrhome
	 *            服务空间名
	 */
	public SolrClientInstance(String solrurl, String solrhome) {
		SOLR_URL = solrurl;
		SOLR_HOME = solrhome;
		try {

			if (!StringUtils.isEmpty(solrurl) && !StringUtils.isEmpty(solrhome)) {
				S_URL = solrurl + "/" + solrhome;
				SOLR_CLIENT = new HttpSolrClient(S_URL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建SolrClient实例方法
	 * 
	 * @Title: getSolrClientInstance
	 * @Description: 静态方法
	 * @param solrurl
	 *            服务地址
	 * @param solrhome
	 *            服务空间名
	 * @return SolrClient SolrClient实例
	 * @author yangs
	 */
	public static SolrClient getSolrClientInstance(String solrurl, String solrhome) {
		SolrClient solrclient = null;
		try {
			if (!StringUtils.isEmpty(solrurl) && !StringUtils.isEmpty(solrhome)) {
				if (SOLR_CLIENT != null && SOLR_URL.equals(solrurl) && SOLR_HOME.equals(solrhome)) {
					solrclient = SOLR_CLIENT;
				} else {
					SOLR_URL = solrurl;
					SOLR_HOME = solrhome;
					S_URL = solrurl + "/" + solrhome;
					SOLR_CLIENT = new HttpSolrClient(S_URL);
					solrclient = SOLR_CLIENT;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solrclient;
	}
}
