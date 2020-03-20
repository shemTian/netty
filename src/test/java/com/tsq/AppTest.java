package com.tsq;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    private AtomicLong atomicLong = new AtomicLong();
    private volatile long vlong = 0;
    @Test
    public void batchAdd() throws InterruptedException {
        CountDownLatch cdw = new CountDownLatch(100);
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(r.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long l = atomicLong.addAndGet(1);
                    vlong ++;
                    System.out.println(Thread.currentThread().getName()+" result:"+l);
                    cdw.countDown();
                }
            });
            t.start();
        }
        cdw.await();
        System.out.println("最终值(atomicLong)："+atomicLong.get()); //100
        System.out.println("最终值(volatileLong):" + vlong); //96
    }
}
