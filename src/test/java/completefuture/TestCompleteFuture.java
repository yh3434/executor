package completefuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;


public class TestCompleteFuture {

    @Test
    public void simpleTest() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        System.out.println(completableFuture.isDone());
        completableFuture.whenCompleteAsync((r, v) -> {
            System.out.println("line 16: " + r);
            System.out.println("line 17: " + v.toString());
        });

        completableFuture.completeExceptionally(new Exception("simple test failed..."));
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(completableFuture.isDone());
        System.out.println("line 19...");
        completableFuture.complete("simple test complete");
        completableFuture.complete("simple test complete2");

    }

    @Test
    public void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(cf.isDone());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cf.isDone());
    }

    @Test
    public void supplyAsyncExample() throws InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("line 56.....");
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsyncExample";
        });
        System.out.println("line 65.... cf.isDone():" + cf.isDone());
        cf.whenCompleteAsync((r, e) -> {
            try {
                System.out.println("line 16: " + r);
                System.out.println("line 17: " + e.toString());
                Thread.sleep(600);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
//        Thread.sleep(600);
        System.out.println("line 71.... cf.isDone():" + cf.isDone());
    }

    @Test
    public void testCompose() {
        CompletableFuture.runAsync(() -> System.out.println(1))
                .thenRun(() -> System.out.println(2));
        CompletableFuture.runAsync(() -> System.out.println(3))
                .thenAccept(resultA -> System.out.println(4));
        CompletableFuture.runAsync(() -> System.out.println(5))
                .thenApply(resultA -> "6");
        CompletableFuture.supplyAsync(() -> "resultA")
                .thenRun(() -> System.out.println(7));
        CompletableFuture.supplyAsync(() -> "8")
                .thenAccept(resultA -> System.out.println(resultA));
        CompletableFuture.supplyAsync(() -> "9")
                .thenApply(resultA -> resultA + " 10")
                .whenCompleteAsync((r, e) -> System.out.println(r));
    }

    @Test
    public void testComposeWithException() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        })
                .exceptionally(ex -> "errorResultA")
                .thenApply(resultA -> resultA + " resultB")
                .thenApply(resultB -> resultB + " resultC")
                .thenApply(resultC -> resultC + " resultD");

        System.out.println(future.join());
    }

    @Test
    public void testParallel() {
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> "resultB");
        cfA.thenAcceptBoth(cfB, (resultA, resultB) -> System.out.println("line:114...." + resultA + resultB));
        cfA.thenCombine(cfB, (resultA, resultB) -> "result A + B").whenCompleteAsync((r, v)
                -> System.out.println("line 115..." + r));
        cfA.runAfterBoth(cfB, () -> System.out.println("line 116..."));
    }

    @Test
    public void testMultiAllOf() {
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cfa's thread is" + Thread.currentThread().getName());
            return "resultA";
        });

        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cfb's thread is" + Thread.currentThread().getName());
            return "resultB";
        });

        CompletableFuture<String> cfC = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cfc's thread is" + Thread.currentThread().getName());
            return "resultC";
        });

        CompletableFuture<String> cfD = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cfd's thread is" + Thread.currentThread().getName());
            return "resultD";
        });
        CompletableFuture<String> cfE = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cfd's thread is" + Thread.currentThread().getName());
            return "resultE";
        });
        final long a = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.allOf(cfA, cfB, cfC, cfD, cfE);
        // 所以这里的 join() 将阻塞，直到所有的任务执行结束
        //由于 allOf 聚合了多个 CompletableFuture 实例，所以它是没有返回值的
        future.join();
        System.out.println("line 75.... execute total" + (System.currentTimeMillis() - a) / 1000 + " s");
    }

    @Test
    public void testMultiAnyOf() {
        CompletableFuture cfA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "resultA";
        });
        CompletableFuture cfB = CompletableFuture.supplyAsync(() -> 123);
        CompletableFuture cfC = CompletableFuture.supplyAsync(() -> "resultC");

        CompletableFuture<Object> future = CompletableFuture.anyOf(cfA, cfB, cfC);
        //会返回第一个future的结果
        Object result = future.join();
        System.out.println(result.toString());
    }

    @Test
    public void testEither() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        CompletableFuture cfA = CompletableFuture.supplyAsync(() -> 123);
        CompletableFuture cfB = CompletableFuture.supplyAsync(() -> 234);
        cfA.acceptEither(cfB, result -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        });

        cfA.acceptEitherAsync(cfB, result -> {
            System.out.println("159.." + result);
        });


//        cfA.acceptEitherAsync(cfB, result -> {
//        }, executorService);
//
//        cfA.applyToEither(cfB, result -> {
//            return result;
//        });
//        cfA.applyToEitherAsync(cfB, result -> {
//            return result;
//        });
//        cfA.applyToEitherAsync(cfB, result -> {
//            return result;
//        }, executorService);
//
//        cfA.runAfterEither(cfA, () -> {
//        });
//        cfA.runAfterEitherAsync(cfB, () -> {
//        });
//        cfA.runAfterEitherAsync(cfB, () -> {
//        }, executorService);
    }
}
