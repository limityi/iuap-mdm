/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonyou.iuap.project.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * @FileName MD5Util.java
 * @Description: 网页数据抓取类
 *
 * @Date 2010年10月25日
 * @author Yan Hua
 * @version 1.0
 * 
 */
public class HttpHelper {

	private static final Log logger = LogFactory.getLog(HttpHelper.class);
	private static HttpClient httpClient;
	private static int maxConnPerHost = 1000;

	private static int maxTotalConn = 1000;

	/** 默认等待连接建立超时，单位:毫秒 */
	private static int connectionTimeout = 20000;

	/** 默认等待数据返回超时，单位:毫秒 */
	private static int soTimeout = 20000;

	/** 路由 */
	public final static int MAX_ROUTE_CONNECTIONS = 400;

	static {
		HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
		connectionManagerParams.setConnectionTimeout(connectionTimeout);
		connectionManagerParams.setSoTimeout(soTimeout);
		connectionManagerParams.setMaxTotalConnections(maxTotalConn);
		connectionManagerParams.setDefaultMaxConnectionsPerHost(maxConnPerHost);
		httpConnectionManager.setParams(connectionManagerParams);
		httpClient = new HttpClient(httpConnectionManager);
		httpClient.getParams().setVersion(HttpVersion.HTTP_1_1);
	}

	/**
	 * POST抓取远程网页数据
	 * 
	 * @param url
	 * @param agrs
	 *            参数列表NameValuePair[]
	 * @return
	 */
	public static String HttpPost(String express, String url, NameValuePair[] agrs, String charset) {
		String html = "";
		// post提交抓取网页
		PostMethod Method = new PostMethod(url);
		Method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset + "");
		Method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		Method.setRequestBody(agrs);
		try {
			// ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
			// Protocol.registerProtocol("https", new Protocol("https", fcty,443));
			int statusCode = httpClient.executeMethod(Method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpPost error express:" + express + " Method failed: " + Method.getStatusLine());
				// return "";
			} else {
				InputStream instream = Method.getResponseBodyAsStream();
				InputStreamReader oneInputSteamReader = new InputStreamReader(instream, charset);
				BufferedReader oneBufferedReader = new BufferedReader(oneInputSteamReader);
				StringBuffer sb = new StringBuffer();
				String temp;
				while ((temp = oneBufferedReader.readLine()) != null) {
					sb.append(temp);
				}
				html = sb.toString();
				return html;
			}
		} catch (HttpException e) {
			logger.error("HttpPost HttpException express:" + express + " " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("HttpPost UnsupportedEncodingException express:" + express + " " + e.getMessage());
		} catch (IOException e) {
			logger.error("HttpPost IOException express:" + express + " " + e.getMessage());
		} finally {
			Method.releaseConnection();
		}
		return charset;
	}

	/**
	 * GET抓取远程网页数据
	 * 
	 * @param url
	 * @return
	 */
	public static String HttpGet(String express, String url, String charset) {
		String html = "";
		// post提交抓取网页
		GetMethod Method = new GetMethod(url);
		Method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset + "");
		Method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// ocs 增加Accept-Language
		Method.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		// aramex 增加cookie
		Method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		Method.setRequestHeader("Cookie", "Lang=zh");
		try {
			// ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
			// Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
			int statusCode = httpClient.executeMethod(Method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("HttpGet error express:" + express + " Method failed: " + Method.getStatusLine());
			} else {
				// 读取内容
				// byte[] responseBody = Method.getResponseBody();
				InputStream instream = Method.getResponseBodyAsStream();
				InputStreamReader oneInputSteamReader = new InputStreamReader(instream, charset);
				BufferedReader oneBufferedReader = new BufferedReader(oneInputSteamReader);
				StringBuffer sb = new StringBuffer();
				String temp;
				while ((temp = oneBufferedReader.readLine()) != null) {
					sb.append(temp);
				}
				html = sb.toString();
				return html;
			}
		} catch (HttpException e) {
			logger.error("HttpGet HttpException express:" + express + " url:" + url + " " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("HttpGet UnsupportedEncodingException express:" + express + " url:" + url + " " + e.getMessage());
		} catch (IOException e) {
			logger.error("HttpGet IOException express:" + express + " url:" + url + " " + e.getMessage());
		} finally {
			Method.releaseConnection();
		}
		return html;
	}
	
}
