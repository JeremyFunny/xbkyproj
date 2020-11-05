package nc.pub.cr.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class WxCorpUtil {
	public static String getAccessToken(String corpid,String corpsecret) throws Exception{
		String accessTokenUrl="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret+"";
		URL url = new URL(accessTokenUrl);
	     HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	     connection.setRequestMethod("GET");
	     connection.setDoOutput(true);
	     connection.setDoInput(true);
	     connection.connect();

	     //获取返回的字符
	     InputStream inputStream = connection.getInputStream();
	     int size =inputStream.available();
	     byte[] bs =new byte[size];
	     inputStream.read(bs);
	     String message=new String(bs,"UTF-8");

	     //获取access_token
	     JSONObject jsonObject = JSONObject.fromObject(message);
	     String accessToken = jsonObject.getString("access_token");
	     String expires_in = jsonObject.getString("expires_in");
	     System.out.println("accessToken="+accessToken);
	     System.out.println("expires_in="+expires_in);
	     return accessToken;
	
	}
	
	public static String getUserid(String accesstoken,String code) throws Exception{
		String accessTokenUrl="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+accesstoken+"&code="+code+"";
		URL url = new URL(accessTokenUrl);
	     HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	     connection.setRequestMethod("GET");
	     connection.setDoOutput(true);
	     connection.setDoInput(true);
	     connection.connect();

	     //获取返回的字符
	     InputStream inputStream = connection.getInputStream();
	     int size =inputStream.available();
	     byte[] bs =new byte[size];
	     inputStream.read(bs);
	     String message=new String(bs,"UTF-8");

	     //获取access_token
	     JSONObject jsonObject = JSONObject.fromObject(message);
	     String userid = jsonObject.getString("UserId");
	     String deviceid = jsonObject.getString("DeviceId");
	     System.out.println("userid="+userid);
	     System.out.println("deviceid="+deviceid);
	     return userid;
	
	}
	public static String getUserMobile(String accesstoken,String userid) throws Exception{
		String accessTokenUrl="https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+accesstoken+"&userid="+userid+"";
		URL url = new URL(accessTokenUrl);
	     HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	     connection.setRequestMethod("GET");
	     connection.setDoOutput(true);
	     connection.setDoInput(true);
	     connection.connect();

	     //获取返回的字符
	     InputStream inputStream = connection.getInputStream();
	     int size =inputStream.available();
	     byte[] bs =new byte[size];
	     inputStream.read(bs);
	     String message=new String(bs,"UTF-8");

	     //获取access_token
	     JSONObject jsonObject = JSONObject.fromObject(message);
	     String mobile = jsonObject.getString("mobile");
	     String name = jsonObject.getString("name");
	     System.out.println("name="+name);
	     System.out.println("mobile="+mobile);
	     return mobile;
	
	}
	
	public static String getRealurl(String corpid,String redirecturi,String state) throws Exception{
		String realurl=java.net.URLEncoder.encode(redirecturi, "UTF-8");
		String accessTokenUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+corpid+""
				+ "&redirect_uri="+realurl+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		System.out.println("url="+accessTokenUrl);
	     return accessTokenUrl;
	
	}
	public static void main(String[] args){
	try {
			String corpid="wwc111261250a8d200";
			String corpsecret="ZtNM82QgCfCVV29tVc-H85fbNT888BHyPztx_m3T91k";
			String redirecturi="http://2140p1z972.imwork.net:43954/pages/user/index";
			String state="A3";
            WxCorpUtil.getAccessToken(corpid,corpsecret);
            WxCorpUtil.getRealurl(corpid, redirecturi, state);
		} catch (Exception e) {
		 e.printStackTrace();
		}
	}
}
