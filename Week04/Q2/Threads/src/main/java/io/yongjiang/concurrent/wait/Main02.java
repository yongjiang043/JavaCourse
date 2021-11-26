package io.yongjiang.concurrent.wait;

/**
 * Created by Yong on 2021/11/26.
 */
public class Main02 {

    private static Integer result;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Thread t = new Thread(new Runnable() {

            public void run() {
                result = sum();
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

        t.start();

        try {
            t.join();

            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
