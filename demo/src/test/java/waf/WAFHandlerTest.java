package waf;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.eova.tools.x;
import org.junit.Test;

/**
 * 模拟扫描器对不存在路径连环请求，用于验证 WAFHandler 软封。
 */
public class WAFHandlerTest {

    @Test
    public void flood404() throws Exception {
        for (int i = 1; i <= 50; i++) {
            try {
                String s = x.http.get("http://127.0.0.1:9090/aaaa/bbbb");
                x.log.info(s);
            } catch (Exception e) {

            }
        }
    }

    private int getStatus(String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        conn.setInstanceFollowRedirects(false);
        try {
            int code = conn.getResponseCode();
            // 消费响应体，避免连接池卡住
            InputStream in = code >= 400 ? conn.getErrorStream() : conn.getInputStream();
            if (in != null) {
                byte[] buf = new byte[1024];
                while (in.read(buf) != -1) {
                    // discard
                }
                in.close();
            }
            return code;
        } finally {
            conn.disconnect();
        }
    }
}
