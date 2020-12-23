package teclan.spring.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONObject;
import teclan.spring.filter.StatusExposingServletResponse;

public class HttpTool {
	private static Logger log = Logger.getLogger(HttpTool.class);
	public static String readJSONString(HttpServletRequest request) {
		String method = request.getMethod();
		if (method == "GET") {
			return request.getQueryString();
		} else {
			StringBuffer json = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					json.append(line);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return json.toString();
		}
	}
	
	public static JSONObject readJSONParam(HttpServletRequest request) {
        String method = request.getMethod();
        if (method == "GET") {
            return JSONObject.parseObject(request.getQueryString());
        } else {
            StringBuffer json = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
            } catch (Exception e) {
				log.error(e.getMessage(), e);
            }
            return JSONObject.parseObject(json.toString());
        }
    }



	public static ResponseEntity<String> GetResponseEntity(String result) {
		HttpHeaders responseHeaders = new HttpHeaders();
		MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
		responseHeaders.setContentType(mediaType);
		return new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
	}

	/**
	 * 发送信令（String）到设备服务器
	 * @param httpUrl
	 * @param json
	 * @return  设备服务器返回结果
	 */
	public static  String postForJson(String httpUrl,String json) {
		//1、创建httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		log.info("-------------HttpTool post2 "+httpUrl+" -------------");
		String strRes = null;
		try {
			Map<String,ContentBody> bodyMap = null;
			bodyMap = new HashMap<String,ContentBody>();
			bodyMap.put("json", new StringBody(json, ContentType.APPLICATION_JSON));

			String requestUri=httpUrl;
			//1、创建请求方法实例，如post就创建httppost

			HttpPost httppost = new HttpPost(requestUri);
			// RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
			//httppost.setConfig(requestConfig);
			MultipartEntityBuilder entityBuilder=MultipartEntityBuilder.create();
			for (Map.Entry<String, ContentBody> entry : bodyMap.entrySet())
			{
				String name= entry.getKey();
				ContentBody body= entry.getValue();
				entityBuilder.addPart(name, body);
			}
			//	entityBuilder.setCharset(Charset.forName("UTF-8"));  //接收方的WEB.XML设置了utf8编码
			HttpEntity reqEntity = entityBuilder.build();
			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);

			try {
				int statusCode=response.getStatusLine().getStatusCode();
				if(statusCode!=200){
					throw new IOException();
				}
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					// 打印响应内容
					strRes = EntityUtils.toString(resEntity);
					log.info("-------------HttpTool post2 responsed---------------");
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
		return strRes;
	}


	/**
	 *
	 * @param response
	 * @param status
	 * @param content
	 * @throws IOException
	 */
	public static void setResponse(StatusExposingServletResponse response,int status,JSONObject content) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json;charset=utf-8");
		ServletOutputStream out  = response.getOutputStream();
		out .write(content.toJSONString().getBytes());
		out .flush();
		out .close();
	}

}
