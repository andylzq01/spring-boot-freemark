package com.wooyoo.learning.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooyoo.learning.common.WeatherResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpClientUtil {

    public static String requestByGetMethod(String uri) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBuilder entityStringBuilder = null;
        try {
            HttpGet get = new HttpGet(uri);
            try {
                CloseableHttpResponse httpResponse = httpClient.execute(get);
                try {
                    HttpEntity entity = httpResponse.getEntity();
                    entityStringBuilder = new StringBuilder();
                    if (null != entity) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8 * 1024);
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            entityStringBuilder.append(line + "/n");
                        }
                    }
                } finally {
                    httpResponse.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return entityStringBuilder.toString();
    }

    public static void main(String[] args) {
        String url = "http://wthrcdn.etouch.cn/weather_mini?city=深圳";
        String result = HttpClientUtil.requestByGetMethod(url);
//        ResponseEntity responseEntity =  restTemplate.getForEntity(url,String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        WeatherResponse weather = null;
//
//        try {
//            weather = mapper.readValue(result, WeatherResponse.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(result);
    }
}  