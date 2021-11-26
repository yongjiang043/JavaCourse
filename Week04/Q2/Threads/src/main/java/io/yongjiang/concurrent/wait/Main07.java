package io.yongjiang.concurrent.wait;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Yong on 2021/11/26.
 */
public class Main07 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(Main07::sum);

        try {
            int result = future.get();

            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
