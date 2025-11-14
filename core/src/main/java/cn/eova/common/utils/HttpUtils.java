/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.eova.tools.x;
import cn.eova.common.utils.io.FileUtil;
import com.jfinal.kit.LogKit;

public class HttpUtils {

    private final static String TYPE_JSON = "application/json";
    private final static String TYPE_XML = "application/xml";
    private final static String TYPE_SAOP = "application/soap+xml";
    private final static String TYPE_TEXT = "text/plain";

    private String charSet = "UTF-8";
    private String contentType = "application/x-www-form-urlencoded";

    private HttpUtils() {
    }

    private HttpUtils(String charSet) {
        this.charSet = charSet;
    }

    /**
     * 默认UTF-8
     *
     * @return
     */
    public static HttpUtils cs() {
        return new HttpUtils();
    }

    /**
     * 自定义编码
     *
     * @param charSet 字符集 UTF-8,GBK
     * @return
     */
    public static HttpUtils cs(String charSet) {
        return new HttpUtils(charSet);
    }

    /**
     * https 域名校验
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private class TrustAnyTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpUtils().new TrustAnyHostnameVerifier();

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = {new HttpUtils().new TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS"); // ("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers, String contentType) throws IOException, NoSuchAlgorithmException,
            NoSuchProviderException, KeyManagementException {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection) conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(30 * 1000);
        conn.setReadTimeout(30 * 1000);

        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty())
            for (Entry<String, String> entry : headers.entrySet())
                conn.setRequestProperty(entry.getKey(), entry.getValue());

        return conn;
    }

    /**
     * Send GET request
     */
    public String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers, this.contentType);
            conn.connect();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    public String get(String url) {
        return get(url, null, null);
    }

    /**
     * Send POST request
     */
    public String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        try {
            return post(url, queryParas, data.getBytes(charSet), headers);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String url, Map<String, String> queryParas, byte[] bt, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers, this.contentType);
            conn.connect();

            OutputStream out = conn.getOutputStream();
            out.write(bt);
            out.flush();
            out.close();

            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, null);
    }

    public String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    public String post(String url, String data) {
        return post(url, null, data, null);
    }

    public String post(String url, Map<String, String> dataParas) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Entry<String, String> entry : dataParas.entrySet()) {
            if (isFirst)
                isFirst = false;
            else
                sb.append("&");

            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value);
        }
        return post(url, null, sb.toString(), null);
    }

    private String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogKit.error(e.getMessage(), e);
                }
            }
        }
    }

    public String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line = null; (line = br.readLine()) != null; ) {
                result.append(line).append("\n");
            }

            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    LogKit.error(e.getMessage(), e);
                }
        }
    }

    /**
     * Build queryString of the url
     */
    private String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty())
            return url;

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }

        for (Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst)
                isFirst = false;
            else
                sb.append("&");

            String key = entry.getKey();
            String value = entry.getValue();
            if (!x.isEmpty(value))
                try {
                    value = URLEncoder.encode(value, charSet);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public String postJson(String url, String json) {
        this.contentType = TYPE_JSON;
        return post(url, null, json, null);
    }

    public String postText(String url, String text) {
        this.contentType = TYPE_TEXT;
        return post(url, null, text, null);
    }

    public String postXml(String url, String xml) {
        this.contentType = TYPE_XML;
        return post(url, null, xml, null);
    }

    public String postSoap(String url, String soap) {
        this.contentType = TYPE_SAOP;
        return post(url, null, soap, null);
    }

    public String postFile(String url, String fileName, File file) {
        this.contentType = "application/octet-stream";

        HashMap<String, String> queryParas = new HashMap<>();
        queryParas.put("fileName", fileName);

        return post(url, queryParas, FileUtil.fileToByte(file), null);
    }

}