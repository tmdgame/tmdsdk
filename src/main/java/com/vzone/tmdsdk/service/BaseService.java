package com.vzone.tmdsdk.service;

import com.vzone.tmdsdk.model.RequestParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by 詹子聪 on 2016/6/2.
 * 网络请求的封装
 */
public class BaseService {
    /**
     * 发送GET请求
     * @param urlAddress 请求的网址
     * @param requestParams 请求参数
     * @return
     */
    protected String submitGetRequest(String urlAddress, RequestParams requestParams) {
        String result = null;
        HttpURLConnection connection = null;
        URL url = null;

        try {
            if (requestParams != null) {
                // 请求参数
                Map<String, String> params = requestParams.getParams();
                int paramSize = params.size();
                int index = 1;
                StringBuilder paramsBuilder = new StringBuilder();
                paramsBuilder.append("?");
                for (String key : params.keySet()) {
                    paramsBuilder.append(key).append("=").append(params.get(key));
                    if (index < paramSize) {
                        paramsBuilder.append("&");
                        index++;
                    }
                }
                url = new URL(urlAddress + paramsBuilder.toString());
            } else {
                url = new URL(urlAddress);
            }

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "text/html");
            // 不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
            connection.setUseCaches(false);
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStream inputStream  = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                result = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     * @return
     */
    protected String submitPostRequest(String urlAddress, RequestParams requestParams) {
        String result = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
            connection.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
            //connection.setRequestProperty("Accept-Language", "zh-cn");
            //connection.setRequestProperty("UA-CPU", "x86");
            //connection.setRequestProperty("Accept-Encoding", "gzip");
            // Connection不要Keep-Alive
            //connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Content-type", "text/html");
            // 不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
            connection.setUseCaches(false);
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);

            // 发送POST请求必须设置这两项
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (requestParams != null) {
                // 请求参数
                Map<String, String> params = requestParams.getParams();
                int paramSize = params.size();
                int index = 1;
                StringBuilder paramsBuilder = new StringBuilder();
                for (String key : params.keySet()) {
                    paramsBuilder.append(key).append("=").append(params.get(key));
                    if (index < paramSize) {
                        paramsBuilder.append("&");
                        index++;
                    }
                }
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(paramsBuilder.toString());
                writer.flush();
                writer.close();
            }

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStream inputStream = null;
                /*if (connection.getContentEncoding() != null && !connection.getContentEncoding().equals("")) {
                    String encode = connection.getContentEncoding().toLowerCase();
                    if (encode != null && !encode.equals("") && encode.indexOf("gzip") >= 0) {
                        inputStream = new GZIPInputStream(connection.getInputStream());
                    }
                }*/

                inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                result = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
