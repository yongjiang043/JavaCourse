package io.yongjiang.concurrent.wait;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Yong on 2021/11/26.
 */
public class Main01 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> future = executorService.submit(new Callable<Integer>() {

            public Integer call() throws Exception {
                return sum();
            }

            private int sum() {
                return fibo(36);
            }

            private int fibo(int a) {
                if (a < 2)
                    return 1;
                return fibo(a-1) + fibo(a-2);
            }
        });

        try {
            Integer result = future.get();

            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
