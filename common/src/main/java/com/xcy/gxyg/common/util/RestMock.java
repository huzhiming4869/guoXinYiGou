package com.xcy.gxyg.common.util;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author HuZhiMing
 */
@Slf4j
@Component
public class RestMock<k, v> {

    @Resource
    private RestTemplate restTemplate;


    /**
     * 生成post请求的JSON请求参数
     * 请求示例:
     * {
     * "id":1,
     * "name":"张耀烽"
     * }
     */
    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap, HttpHeaders httpHeaders) {
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);
//        System.out.println(JSONUtil.toJsonStr(httpEntity));
        log.info(JSONUtil.toJsonStr(httpEntity));
        return httpEntity;
    }

    /**
     * 生成post请求的JSON请求参数
     * 请求示例:
     * {
     * "id":1,
     * "name":"张耀烽"
     * }
     *
     * @return
     */
    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {
        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json");
        httpHeaders.setContentType(type);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);
//        System.out.println(JSONUtil.toJsonStr(httpEntity));
        return httpEntity;
    }


    /**
     * 生成get参数请求url
     * 示例：https://0.0.0.0:80/api?u=u&o=o
     * 示例：https://0.0.0.0:80/api
     *
     * @param uri    请求的uri 示例: 0.0.0.0:80
     * @param params 请求参数
     * @return
     */
    public String generateRequestParameters(String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(uri);
        if (!params.isEmpty()) {
            sb.append("?");
            for (Map.Entry map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }

    /**
     * get请求、请求参数为?拼接形式的
     * <p>
     * 最终请求的URI如下：
     * <p>
     *
     * @return
     */
    public String sendGet(String url, Map<String, String> uriMap) {

        ResponseEntity responseEntity = restTemplate.getForEntity
                (generateRequestParameters(
                        url, uriMap), String.class);

        return (String) responseEntity.getBody();
    }

    /**
     * post请求、请求参数为json
     *
     * @return
     */
    public String sendPost(String url, Map<String, String> uriMap) {

        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        url,
                        generatePostJson(uriMap),
                        String.class
                );
        return apiResponse.getBody();
    }

    /**
     * post请求、请求参数为json
     */
    public String sendPost(String url, Map<String, String> uriMap, HttpHeaders httpHeaders) {

        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        url,
                        generatePostJson(uriMap, httpHeaders),
                        String.class
                );
        return apiResponse.getBody();
    }

    /**
     * post请求并得到返回结果
     *
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String output) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            return buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }

            RequestConfig requestConfig = getRequestConfig();
            httpPost.setConfig(requestConfig);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPostHeader(String url, Map<String, String> param, String header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Authorization", header);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }

            log.info(JSONUtil.toJsonStr(httpPost));
            System.out.println(JSONUtil.toJsonStr(httpPost));
            RequestConfig requestConfig = getRequestConfig();
            httpPost.setConfig(requestConfig);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String sslDoPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key) + ""));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }

            RequestConfig requestConfig = getRequestConfig();
            httpPost.setConfig(requestConfig);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            System.out.println("ssl------------------");
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    public static String doPost(String url, String jsonString) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);
        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);
        return doHttpJson(httpPost);
    }


    public static String doHttpJson(HttpRequestBase httpBase) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            response = httpClient.execute(httpBase);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return null;
    }

    public static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        try {
            // 释放资源
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RequestConfig getRequestConfig() {
        // 配置信息
        return RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(3000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(2000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000).build();
    }

    public static String doPost(String url) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = getRequestConfig();
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public String doGet(String url, Map<String, String> map) {
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Header header = new BasicHeader(entry.getKey(), entry.getValue());
            httpGet.setHeader(header);
        }
        return doHttpJson(httpGet);
    }
}
