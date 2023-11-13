package TEST_API;

import java.io.IOException;
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
import kr.or.koroad.dlv.util.Base64;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;

public class test {

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

    // 개발,운영 테스트
    public static String postValidata(String access_token)
            throws URISyntaxException, IOException, InvalidKeyException, ParseException {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureHttpClient2(httpClientBuilder);
        String rtn_msg = "";
        String reqIp = "203.243.39.19";//"203.243.39.19";dlv.koroad.or.kr
        RedirectStrategy Re_url = new DefaultRedirectStrategy();
        CloseableHttpClient client = httpClientBuilder.setRedirectStrategy(Re_url)
                .build();

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String request_date = format.format(now);

        String authHeader = OAuth2ClientUtil.generateBearerTokenHeaderString(access_token);
        String jsonBody = "{\"f_license_no\" : \"112022043610\"," +
                "\"f_resident_name\" : \"양설믜\"," +
                "\"f_resident_date\" : \"970323\"," +
                "\"f_seq_no\" : \"123456\"," +
                "\"f_licn_con_code\" : \"32\"," +
                "\"f_from_date\" : \"" + request_date + "\"," +
                "\"f_to_date\" : \"" + request_date + "\"}";

        String client_secret = "b9e8207f657fc4681653dd4bc728ce83e8687c43ebfaead9d5969bc6e2ccbc99";//"b9e8207f657fc4681653dd4bc728ce83e8687c43ebfaead9d5969bc6e2ccbc99";//"b9e8207f657fc4681653dd4bc728ce83e8687c43ebfaead9d5969bc6e2ccbc99";1babd46e42d11d9b953e5c8cf74bdfbc0ed8e08e0e733259a529a4f89401d21b
        ARIACipher256 ac = new ARIACipher256(Base64.encode(client_secret.getBytes()));
        byte[] encBody = ac.encrypt(jsonBody.getBytes("UTF-8"));
        String encStr = Base64.encode(encBody);
        System.out.println("암호화 데이터");
        System.out.println(encStr);

    
        byte[] decBody2 = Base64.decode(encStr);
        byte[] decBody1 = ac.decrypt(decBody2);
        String decStr = new String(decBody1,"EUC-KR");
        System.out.println("복호화 데이터");
        System.out.println(decStr);


        String reqStr = "{\"header\" : {\"f_send_cnt\" : \"1\",\"f_request_date\" : \"" + request_date
                + "\",\"f_pin_info\" : \""
                + Base64.encode("1234567890:1234565".getBytes()) + "\"},\"body\" : \"" + encStr + "\"}";

        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(reqIp).setPath("/api/onevalidatorv2.do");
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

        byte[] encBody1 = Base64.decode((String) jsonObject1.get("body"));
        byte[] decBody = ac.decrypt(encBody1);
        JSONObject bodys = (JSONObject) parser.parse(new String(decBody, "UTF-8"));
        JSONObject headers = (JSONObject) jsonObject1.get("header");
        System.out.println("header :" + headers.toJSONString());
        System.out.println("body :" + bodys.toJSONString());

        rtn_msg = jsonObject.toString();
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
            String client_id = "41b74fc6-bef6-4402-9a66-c12748b91a6c";//"41b74fc6-bef6-4402-9a66-c12748b91a6c";//"41b74fc6-bef6-4402-9a66-c12748b91a6c";84485bc9-3315-473f-9629-a55fa786b4f9
            String client_secret = "b9e8207f657fc4681653dd4bc728ce83e8687c43ebfaead9d5969bc6e2ccbc99";//"b9e8207f657fc4681653dd4bc728ce83e8687c43ebfaead9d5969bc6e2ccbc99";//"b9e8207f657fc4681653dd4bc728ce83e8687c43ebfaead9d5969bc6e2ccbc99";1babd46e42d11d9b953e5c8cf74bdfbc0ed8e08e0e733259a529a4f89401d21b

            String authHeader = OAuth2ClientUtil.generateBasicAuthHeaderString(client_id, client_secret);

            System.out.println("토큰발급 시작");
            try {
                String reqIp = "203.243.39.19";//"203.243.39.19";dlv.koroad.or.kr

                URIBuilder builder = new URIBuilder();
                builder.setScheme("http").setHost(reqIp).setPath("/oauth2/token2")
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
