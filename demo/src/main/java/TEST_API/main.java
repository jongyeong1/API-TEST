package TEST_API;
//https://dlv.koroad.or.kr/MagicLine4Web/ML4Web/install_bin/magicline4npiz.exe, https://dlv.koroad.or.kr/MagicLine4Web/ML4Web/install_bin/magicline4npiz.pkg
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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kr.co.grutech.anyauth2.client.OAuth2ClientUtil;
import kr.or.koroad.dlv.crypt.aria.cipher.ARIACipher256;
import kr.or.koroad.dlv.util.Base64;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import TEST_API.devTest;
import TEST_API.prodTest;
import TEST_API.redirectTest;
import TEST_API.webTest;
import TEST_API.test;

public class main {    

    public static void main(String[] args) throws URISyntaxException, IOException, InvalidKeyException, ParseException {
        
        //test test_api = new test();
        devTest test_api = new devTest();
        //prodTest test_api = new prodTest();
        //redirectTest test_api = new redirectTest();
        //webTest test_api = new webTest();

        String result = "";

        try{
            result= test_api.TestStart();
            System.out.println(result);

            //note obj = new note();
            
        } catch (Exception e){
            System.out.println("에러발생");
        }
        
    }

}
