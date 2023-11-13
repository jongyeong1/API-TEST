package TEST_API;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import kr.co.grutech.anyauth2.client.OAuth2ClientUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class redirectTest {
    public static void HttpURLConnectionGet(String strURL, String strParams) {
        try {
        URL url = new URL(strURL + "?" + strParams);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    
        String client_id = "ec456ed8-0292-4411-b01b-4f75281f9746";//
        String client_secret =
        "a24e2b30b7df5a95e1c3e747c3f4aef7bd71f2019955d78c9e175f6795a75494";//
    
        String authHeader = OAuth2ClientUtil.generateBasicAuthHeaderString(client_id,
        client_secret);
    
        conn.setRequestMethod("GET");
    
        conn.setRequestProperty("Authorization", authHeader);
        conn.setRequestProperty("Access-Control-Allow-Origin", "*");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setInstanceFollowRedirects(false);
    
        Charset charset = Charset.forName("UTF-8");
        BufferedReader in = new BufferedReader(new
        InputStreamReader(conn.getInputStream(), charset));
    
        int responseCode = conn.getResponseCode();
        System.out.println("응답코드 : " + responseCode);
        System.out.println("응답메세지 : " + conn.getResponseMessage());
        System.out.println("에러메세지 : " + conn.getErrorStream());
        System.out.println("리디렉션주소 : " + conn.getHeaderField("Location"));
    
        String inputLine;
        StringBuffer response = new StringBuffer();
    
        while((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        }
    
        in.close();
    
        } catch (MalformedURLException e) {
        //URL
        e.printStackTrace();
        } catch (IOException e) {
        // HttpURLConnection
        e.printStackTrace();
        }
    
        }
        public void HttpURLConnectionPost(String strURL, String strParams) {
        try {
        URL url = new URL(strURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    
        conn.setRequestMethod("POST");
        //con.setRequestProperty("Authorization", alpha);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept-Language", "ko-kr");
        conn.setRequestProperty("Access-Control-Allow-Origin", "*");
        conn.setRequestProperty("Content-Type", "application/json");
    
        conn.setConnectTimeout(10000); // 커넥션 timeout 10초
        conn.setReadTimeout(5000); //컨텐츠 조회시 timeout 5초
    
        conn.setDoOutput(true); //항상 갱신된 내용 가져오도록 true로 설정
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(strParams); //파라미터 write
        wr.flush();
        wr.close();
    
        //int responseCode = conn.getResponseCode();
    
        Charset charset = Charset.forName("UTF-8");
        BufferedReader in = new BufferedReader(new
        InputStreamReader(conn.getInputStream(), charset));
    
        String inputLine;
        StringBuffer response = new StringBuffer();
    
        while((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        }
    
        in.close();
    
        System.out.println(response.toString());
    
        } catch (MalformedURLException e) {
        //URL
        e.printStackTrace();
        } catch (IOException e) {
        //HttpURLConnection
        e.printStackTrace();
        }
    
        }
        public void HttpsURLConnectionPost(String strURL, String strParams) {
        //HttpsURLConnection은 HttpURLConnection을 상속 받는다.
        //사용 방식은 동일하나 http url과 https url이 다른 부분이다.
    
        try {
        URL url = new URL(strURL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    
        conn.setRequestMethod("POST");
        //con.setRequestProperty("Authorization", alpha);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept-Language", "ko-kr");
        conn.setRequestProperty("Access-Control-Allow-Origin", "*");
        conn.setRequestProperty("Content-Type", "application/json");
    
        conn.setConnectTimeout(10000); // 커넥션 timeout 10초
        conn.setReadTimeout(5000); //컨텐츠 조회시 timeout 5초
    
        conn.setDoOutput(true); //항상 갱신된 내용 가져오도록 true로 설정
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(strParams); //파라미터 write
        wr.flush();
        wr.close();
    
        //int responseCode = conn.getResponseCode();
    
        Charset charset = Charset.forName("UTF-8");
        BufferedReader in = new BufferedReader(new
        InputStreamReader(conn.getInputStream(), charset));
    
        String inputLine;
        StringBuffer response = new StringBuffer();
    
        while((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        }
    
        in.close();
    
        System.out.println(response.toString());
    
        } catch (MalformedURLException e) {
        //URL
        e.printStackTrace();
        } catch (IOException e) {
        //HttpsURLConnection
        e.printStackTrace();
        }
    
        }
    
        public static String TestStart() throws MalformedURLException,
        IOException {
    
        HttpURLConnectionGet("http://203.243.39.19/oauth2/token2","grantType=password");

        return "정상";
    
        }
}
