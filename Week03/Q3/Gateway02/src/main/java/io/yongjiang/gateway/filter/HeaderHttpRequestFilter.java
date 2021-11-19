package io.yongjiang.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.concurrent.atomic.AtomicInteger;

public class HeaderHttpRequestFilter implements HttpRequestFilter {

    private static AtomicInteger requestCount = new AtomicInteger(0);

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        int count = requestCount.incrementAndGet();

        System.out.println("This is the " + count + " time request");
    }
}
