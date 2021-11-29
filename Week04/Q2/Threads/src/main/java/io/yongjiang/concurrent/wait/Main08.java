package io.yongjiang.concurrent.wait;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Yong on 2021/11/29.
 */
public class Main08 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        FutureTask<Integer> futureTask = new FutureTask(()-> sum());

        new Thread(futureTask).start();

        try {
            int result = futureTask.get();

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
