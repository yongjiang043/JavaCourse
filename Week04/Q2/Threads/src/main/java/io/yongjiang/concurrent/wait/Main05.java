package io.yongjiang.concurrent.wait;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Yong on 2021/11/26.
 */
public class Main05 {
    private static int result;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(1);
        new Task(latch).start();

        try {
            latch.await();

            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Task extends Thread {
        private CountDownLatch latch;

        Task(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {
            result = sum();
            latch.countDown();
        }

        private int sum() {
            return fibo(36);
        }

        private int fibo(int a) {
            if (a < 2)
                return 1;
            return fibo(a-1) + fibo(a-2);
        }
    }
}


