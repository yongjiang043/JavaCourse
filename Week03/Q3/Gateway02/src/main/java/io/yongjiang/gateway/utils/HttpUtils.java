package io.yongjiang.gateway.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * http工具类
 *
 * @author : Yong.J
 */
public class HttpUtils {

    private static final String ENCODING = "UTF-8";

    private static final int CONNECT_TIMEOUT = 300000;

    private static final int SOCKET_TIMEOUT = 600000;

    private static final String BAD_RESPONSE = "Bad response";

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     */
    public static String doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     */
    public static String doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        packageHeader(headers, httpGet);

        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     */
    public static String doPost(String url) throws Exception {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @param jsonBody 请求体
     */
    public static String doPost(String url, Map<String, String> params, String jsonBody) throws Exception {
        return doPost(url, null, params, jsonBody);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @param jsonBody 请求体
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, String> params, String jsonBody) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpPost httpPost = new HttpPost(uriBuilder.build());

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        if (jsonBody != null && jsonBody.length() > 0) {
            httpPost.setEntity(new StringEntity(jsonBody, ENCODING));
        }

        if (headers == null) {
            headers = new HashMap<>(16);
        }
        headers.put("Content-Type", "application/json;charset=UTF-8");
        packageHeader(headers, httpPost);

        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        // 封装请求头
        if (headers != null) {
            Set<Entry<String, String>> entrySet = headers.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    private static String getHttpClientResult(CloseableHttpResponse httpResponse,
        CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        httpResponse = httpClient.execute(httpMethod);

        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            if (httpResponse.getEntity() != null) {
                return EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
        }

        return BAD_RESPONSE;
    }

    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

}
