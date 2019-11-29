package forkjoinpool;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:ForkJoinPoolAction <br/>
 * Function: 使用ForkJoinPool完成一个任务的分段执行
 * 简单的打印0-300的数值。用多线程实现并行执行
 * Date:     2017年12月4日 下午2:26:55 <br/>
 *
 * @author prd-lxw
 * @version 1.0
 * @see
 * @since JDK 1.7
 */
public class ForkJoinPoolAction {

    public static void main(String[] args) throws Exception {
        PrintTask task = new PrintTask(0, 100_000);
        //创建实例，并执行分割任务
        ForkJoinPool pool = new ForkJoinPool();
        final Integer sum = pool.submit(task).get();
        System.out.println("sum is" + sum);
        //线程阻塞，等待所有任务完成
        pool.awaitTermination(20, TimeUnit.SECONDS);
        pool.shutdown();
        System.out.println();
    }
}

class PrintTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000; //最多只能打印50个数
    private int start;
    private int end;


    public PrintTask(int start, int end) {
        super();
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += i;
                System.out.println(Thread.currentThread().getName() + "-sum from" + start + "to" + end + " with result " + sum);
            }
        } else {
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            //并行执行两个“小任务”
            left.fork();
            right.fork();
            sum += left.join();
            sum += right.join();
        }
        return sum;
    }

}
