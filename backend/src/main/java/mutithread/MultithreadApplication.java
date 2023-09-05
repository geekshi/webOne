package mutithread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MultithreadApplication {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5, 5, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
    );

    private static AtomicInteger count = new AtomicInteger();

    private CountDownLatch latch = new CountDownLatch(50);

    public static void main(String[] args) {
        new MultithreadApplication().run();
    }

    public void run() {
        for(int i = 0; i < 50; i++) {
            threadPoolExecutor.submit(() -> {
                try {
                    log.info("Current thread: {}, count: {}", Thread.currentThread().getName(), count.incrementAndGet());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
            log.info("final count: {}", count.get());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            threadPoolExecutor.shutdown();
        }
    }
}
