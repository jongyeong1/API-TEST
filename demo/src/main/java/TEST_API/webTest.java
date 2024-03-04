package TEST_API;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kr.co.grutech.anyauth2.client.OAuth2ClientUtil;
import kr.or.koroad.dlv.crypt.aria.cipher.ARIACipher256;
//import kr.or.koroad.dlv.util.Base64;

import java.util.Map;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class webTest {

    public static void configureHttpClient2(HttpClientBuilder clientBuilder) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);

            clientBuilder.setSSLContext(ctx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String libcall(String key, String encText)
            throws URISyntaxException, IOException, InvalidKeyException, ParseException {

        Encoder encode = Base64.getEncoder();
        Decoder decode = Base64.getDecoder();
        String originalStr = encText;

        key = new String(encode.encode(key.getBytes()));
        encText = new String(encode.encode(encText.getBytes()));

        byte[] bytes = originalStr.getBytes("utf-8");
        originalStr = new String(bytes);

        String[] charSet = {"utf-8", "euc-kr", "ksc5601", "iso-8859-1", "x-windows-949"};
        for(int i = 0; i<charSet.length; i++){
	        for(int j = 0; j<charSet.length; j++){
	        	try{ 
		        	System.out.println("[" + charSet[i] + "," + charSet[j] + "]" + new String(originalStr.getBytes(charSet[i]), charSet[j]));
		        } catch (UnsupportedEncodingException e){
			        e.printStackTrace();
        		}
	        }
        }

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureHttpClient2(httpClientBuilder);
        String rtn_msg = "";
        String reqIp = "localhost:9028";
        RedirectStrategy Re_url = new DefaultRedirectStrategy();
        CloseableHttpClient client = httpClientBuilder.setRedirectStrategy(Re_url)
                .build();

        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(reqIp).setPath("encryptv2.do");
        URI uri = builder.build();
        HttpPost httpPost = new HttpPost(uri); 
        System.out.println(httpPost.getURI());
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("traditional", "false");
        httpPost.addHeader("crossOrigin", "true");

        String reqStr = "{\"key\" : \""+key+"\","+
                          "\"encText\" : \""+encText+ "\"}";

        httpPost.setEntity(new StringEntity(reqStr));
        // StringRequestEntity postingString = new
        // StringRequestEntity(reqStr,"application/json","UTF-8");
        System.out.println("요청발생");
        CloseableHttpResponse resp = client.execute(httpPost);
        System.out.println("요청성공");
        int return_code = resp.getStatusLine().getStatusCode();
        String return_msg = "" + EntityUtils.toString(resp.getEntity(), "UTF-8");

        System.out.println("응답코드 : " + return_code);
        System.out.println("응답 데이터 : " + return_msg);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();

        parser = new JSONParser();
        jsonObject = (JSONObject) parser.parse(return_msg);

        rtn_msg = (String)jsonObject.get("encText");
        return rtn_msg;

    }

    public static String libcall2(String key, String decText)
            throws URISyntaxException, IOException, InvalidKeyException, ParseException {

        Encoder encode = Base64.getEncoder();
        Decoder decode = Base64.getDecoder();

        key = new String(encode.encode(key.getBytes()));

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureHttpClient2(httpClientBuilder);
        String rtn_msg = "";
        String reqIp = "localhost:9028";
        RedirectStrategy Re_url = new DefaultRedirectStrategy();
        CloseableHttpClient client = httpClientBuilder.setRedirectStrategy(Re_url)
                .build();

        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(reqIp).setPath("decryptv2.do");
        URI uri = builder.build();
        HttpPost httpPost = new HttpPost(uri); 
        System.out.println(httpPost.getURI());
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("traditional", "false");
        httpPost.addHeader("crossOrigin", "true");

        String reqStr = "{\"key\" : \""+key+"\","+
                          "\"decText\" : \""+decText+ "\"}";

        httpPost.setEntity(new StringEntity(reqStr));
        // StringRequestEntity postingString = new
        // StringRequestEntity(reqStr,"application/json","UTF-8");
        System.out.println("요청발생");
        CloseableHttpResponse resp = client.execute(httpPost);
        System.out.println("요청성공");
        int return_code = resp.getStatusLine().getStatusCode();
        String return_msg = "" + EntityUtils.toString(resp.getEntity(), "UTF-8");

        System.out.println("응답코드 : " + return_code);
        System.out.println("응답 데이터 : " + return_msg);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();

        parser = new JSONParser();
        jsonObject = (JSONObject) parser.parse(return_msg);

        rtn_msg = (String)jsonObject.get("decText");
        return rtn_msg;

    }

    // 개발 웹서비스 호출 테스트
    public static String postValidata(String access_token)
            throws URISyntaxException, IOException, InvalidKeyException, ParseException {

        Encoder encode = Base64.getEncoder();
        Decoder decode = Base64.getDecoder();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureHttpClient2(httpClientBuilder);
        String rtn_msg = "";
        String reqIp = "dlv.koroad.or.kr";
        RedirectStrategy Re_url = new DefaultRedirectStrategy();
        CloseableHttpClient client = httpClientBuilder.setRedirectStrategy(Re_url)
                .build();

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String request_date = format.format(now);

        String authHeader = OAuth2ClientUtil.generateBearerTokenHeaderString(access_token);
        String jsonBody = "{\"f_license_no\" : \"151900335100\"," +
                "\"f_resident_name\" : \"이종영\"," +
                "\"f_resident_date\" : \"970323\"," +
                "\"f_seq_no\" : \"123456\"," +
                "\"f_licn_con_code\" : \"32\"," +
                "\"f_from_date\" : \"" + request_date + "\"," +
                "\"f_to_date\" : \"" + request_date + "\"}";
        
        String client_secret = "1babd46e42d11d9b953e5c8cf74bdfbc0ed8e08e0e733259a529a4f89401d21b";
        String encStr = libcall(client_secret,jsonBody);
        System.out.println("암호화 데이터");
        System.out.println(encStr);

        String reqStr = "{\"header\" : {\"f_send_cnt\" : \"1\",\"f_request_date\" : \"" + request_date
                + "\",\"f_pin_info\" : \""
                + new String(encode.encode("1234567890:1234565".getBytes())) + "\"},\"body\" : \"" + encStr + "\"}";

        URIBuilder builder = new URIBuilder();
        builder.setScheme("https").setHost(reqIp).setPath("/api/onevalidatorv2.do");
        URI uri = builder.build();
        HttpPost httpPost = new HttpPost(uri);
        System.out.println(httpPost.getURI());
        httpPost.addHeader("Authorization", authHeader);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("traditional", "false");
        httpPost.addHeader("crossOrigin", "true");
        httpPost.setEntity(new StringEntity(reqStr));
        // StringRequestEntity postingString = new
        // StringRequestEntity(reqStr,"application/json","UTF-8");
        System.out.println("요청발생");
        CloseableHttpResponse resp = client.execute(httpPost);
        System.out.println("요청성공");
        int return_code = resp.getStatusLine().getStatusCode();
        String return_msg = "" + EntityUtils.toString(resp.getEntity(), "UTF-8");

        System.out.println("응답코드 : " + return_code);
        System.out.println("응답 데이터 : " + return_msg);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        parser = new JSONParser();
        jsonObject1 = (JSONObject) parser.parse(return_msg);
        jsonObject = (JSONObject) jsonObject1.get("header");

        String jsonBody2= (String) jsonObject1.get("body");
        System.out.println("body first :" + jsonBody2);
        String bodys = libcall2(client_secret,jsonBody2);
        JSONObject headers = (JSONObject) jsonObject1.get("header");
        System.out.println("header :" + headers.toJSONString());
        System.out.println("body :" + new String(decode.decode(bodys)));

        rtn_msg = "{ " + headers.toJSONString() +  " }" + "{ " + new String(decode.decode(bodys)) +  " }";
        return rtn_msg;
    }

    public static String TestStart() throws URISyntaxException, IOException, InvalidKeyException, ParseException {
        {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            RedirectStrategy Re_url = new DefaultRedirectStrategy();
            CloseableHttpClient client = httpClientBuilder.setRedirectStrategy(Re_url)
                    .build();

            Map<String, Object> jsonMap = new HashMap<String, Object>();
            String client_id = "84485bc9-3315-473f-9629-a55fa786b4f9";
            String client_secret = "1babd46e42d11d9b953e5c8cf74bdfbc0ed8e08e0e733259a529a4f89401d21b";

            String authHeader = OAuth2ClientUtil.generateBasicAuthHeaderString(client_id, client_secret);

            System.out.println("토큰발급 시작");
            try {
                String reqIp = "dlv.koroad.or.kr";

                URIBuilder builder = new URIBuilder();
                builder.setScheme("https").setHost(reqIp).setPath("/oauth2/token2")
                        .setParameter("grantType", "password");
                URI uri = builder.build();
                HttpGet httpget = new HttpGet(uri);
                System.out.println(httpget.getURI());
                httpget.addHeader("Authorization", authHeader);
                httpget.addHeader("Content-type", "application/json");
                System.out.println("전역설정");
                CloseableHttpResponse resp = client.execute(httpget);
                System.out.println("토큰요청");
                int return_code = resp.getStatusLine().getStatusCode();
                String return_msg = "" + EntityUtils.toString(resp.getEntity(), "UTF-8");

                System.out.println("응답코드 : " + return_code);
                System.out.println("응답 데이터 : " + return_msg);

                JSONParser parser = new JSONParser();
                JSONObject jsonObject1 = new JSONObject();
                JSONObject jsonObject = new JSONObject();

                if (return_code == HttpStatus.SC_OK) { // resp.getStatusLine().getStatusCode() != 200
                    System.out.println("리다이렉트 응답");
                    parser = new JSONParser();
                    jsonObject1 = (JSONObject) parser.parse(return_msg);
                    jsonObject = (JSONObject) jsonObject1.get("header");
                    System.out.println(jsonObject.get("f_access_token"));
                    System.out.println(jsonObject.get("f_rtn_status"));
                    System.out.println(jsonObject.get("f_token_type"));

                    System.out.println("검증요청");
                    String result = postValidata((String) jsonObject.get("f_access_token"));
                    System.out.println("결과 : " + result);

                } else {
                    System.out.println("연결실패 실패코드 : " + return_code);
                }

                jsonMap.put("rtn", "S");
                jsonMap.put("msg", "");
                return "정상";
            } catch (Exception e) {
                jsonMap.put("rtn", "F");
                jsonMap.put("msg", "에러 발생. :: " + e.getMessage());
                return "에러";
            } finally {
                try {
                    if (client != null)
                        client.close();
                } catch (IOException ee) {
                    System.out.println("웹서비스 종료 오류");
                    return "에러";
                }
            }
        }
    }
}
