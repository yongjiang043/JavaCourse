package io.yongjiang.concurrent.wait;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Yong on 2021/11/26.
 */
public class Main06 {
    private static int result;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        CyclicBarrier barrier = new CyclicBarrier(2);
        new Task(barrier).start();

        try {
            barrier.await();

            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    static class Task extends Thread {
        private CyclicBarrier barrier;

        Task(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {
            result = sum();
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
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


