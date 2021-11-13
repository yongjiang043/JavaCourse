package io.yongjiang.httpclient;

import org.junit.Test;

public class HttpUtilsTester {

    @Test
    public void testGet() {
        String url = "https://www.baidu.com";

        try {
            String response = HttpUtils.doGet(url);

            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Exception happens" + e.getMessage());
        }
    }
}
