package teclan.spring.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientTool {
	private static Logger log = Logger.getLogger(HttpClientTool.class);

	private static PropertyConfigUtil propertyconfigUtil = PropertyConfigUtil
			.getInstance("config.properties");

	public static String post(String interfaceName, String strQueryJson){
		String targetProjectUrl = propertyconfigUtil.getValue("targetProjectUrl");
		return post(targetProjectUrl, interfaceName, strQueryJson);
	}
	
	public static String postToStatistics(String interfaceName, String strQueryJson){
		String targetProjectUrl = propertyconfigUtil.getValue("StatisticsUrl");
		return post(targetProjectUrl, interfaceName, strQueryJson);
	}

	public static String post(String targetProjectUrl, String interfaceName,
			String strQueryJson) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String requestUrl = targetProjectUrl + interfaceName;
		String resp = new String();
		try {
			// 创建HttpPost.
			HttpPost httppost = new HttpPost(requestUrl);
			httppost.setHeader("Accept-Encoding", "gzip, deflate");
			httppost.setHeader("Accept-Language", "zh-CN");
			httppost.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			httppost.setHeader("Content-Type", "application/json; charset=UTF-8");//发送的格式，内容
			
			HttpEntity sendEntity;
			sendEntity = new StringEntity(strQueryJson, "UTF-8");
			httppost.setEntity(sendEntity);
			log.info("executing request " + httppost.getURI());
			log.info("param:"+strQueryJson);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				log.info("--------------------------------------");
				// 打印响应状态
				log.info(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					log.info("Response content length: "
							+ entity.getContentLength());
					// 打印响应内容
					resp = EntityUtils.toString(entity);
					log.info("Response content: " + resp);
				}
				log.info("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(), e);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return resp;
	}

	public static String get(String interfaceName, String strQueryJson) throws Exception {
		String targetProjectUrl = propertyconfigUtil.getValue("targetProjectUrl");
		return get(targetProjectUrl, interfaceName, strQueryJson);
	}
	
	public static String get(String targetProjectUrl, String interfaceName,
			String strQueryJson) throws Exception {
		String requestUrl = targetProjectUrl + interfaceName;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resp = "";
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(requestUrl);
			StringEntity strEntity = new StringEntity(strQueryJson,
					ContentType.APPLICATION_JSON);
			String str = java.net.URLEncoder.encode(
					EntityUtils.toString(strEntity), "UTF-8");
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + str.toString()));
			log.info("executing request " + httpget.getURI());
			log.info("param:"+strQueryJson);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				log.info("--------------------------------------");
				// 打印响应状态
				log.info(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					log.info("Response content length: "
							+ entity.getContentLength());
					// 打印响应内容
					resp = EntityUtils.toString(entity);
					log.info("Response content: " + resp);
				}
				log.info("------------------------------------");
			} finally {
				response.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return resp;
	}
	
	
	public static String put(String interfaceName, String strQueryJson) {
		String targetProjectUrl = propertyconfigUtil.getValue("targetProjectUrl");
		return put(targetProjectUrl, interfaceName, strQueryJson);
	}

	public static String put(String targetProjectUrl, String interfaceName,
			String strQueryJson) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String requestUrl = targetProjectUrl + interfaceName;
		String resp = "";
		try {
			// 创建HttpPut.
			HttpPut httpput = new HttpPut(requestUrl);
			httpput.setHeader("Accept-Encoding", "gzip, deflate");
			httpput.setHeader("Accept-Language", "zh-CN");
			httpput.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			HttpEntity sendEntity;
			sendEntity = new StringEntity(strQueryJson, "UTF-8");
			httpput.setEntity(sendEntity);
			log.info("executing request " + httpput.getURI());
			log.info("param:"+strQueryJson);

			CloseableHttpResponse response = httpclient.execute(httpput);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				log.info("--------------------------------------");
				// 打印响应状态
				log.info(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					log.info("Response content length: "
							+ entity.getContentLength());
					// 打印响应内容
					resp = EntityUtils.toString(entity);
					log.info("Response content: " + resp);
				}
				log.info("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(), e);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return resp;
	}
	
	
	public static String postTOCas(String interFaceName, Map<String, String> map) { 
        CloseableHttpClient httpclient = HttpClients.createDefault(); 
        
        String caseCluesuri = propertyconfigUtil.getValue("casProjectUrl");
        
        String resp="";
        String requestUri=caseCluesuri+interFaceName;
        HttpPost httppost = new HttpPost(requestUri); 
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        if(map!=null){
	        for (Map.Entry<String, String> entry : map.entrySet()) {  
	        	formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	        }  
        }
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            log.info("executing request " + httppost.getURI()); 
            
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                	resp=EntityUtils.toString(entity, "UTF-8");
                	log.info("--------------------------------------");  
                	log.info("Response content: " + resp);  
                	log.info("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
			log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e1) {  
			log.error(e1.getMessage(), e1);
        } catch (IOException e) {  
			log.error(e.getMessage(), e);
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
				log.error(e.getMessage(), e);
            }  
        }  
        return resp;
    }  
}
