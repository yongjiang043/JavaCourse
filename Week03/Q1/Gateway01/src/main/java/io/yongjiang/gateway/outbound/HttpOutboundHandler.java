package io.yongjiang.gateway.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.yongjiang.gateway.utils.HttpUtils;

import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler {

    private ExecutorService proxyService;
    private String backendUrl;

    public HttpOutboundHandler(String backends) {

        this.backendUrl = formatUrl(backends);

        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 100;
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"));

    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }
    
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = backendUrl + fullRequest.uri();
        proxyService.submit(()->fetchGet(fullRequest, ctx, url));
    }
    
    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {

        try {
            String result = HttpUtils.doGet(url);
            handleResponse(inbound, ctx, result);
        } catch (Exception e) {
            System.out.println("Exception when do get request" + e.getMessage());
            exceptionCaught(ctx, e);
        }
    }
    
    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, String value) throws Exception {
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (Exception e) {
            System.out.println("Exception when handle response" + e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
