package MultiThreading;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueuesLearning implements Runnable {

    private static final Map<String, BlockingQueue<String>> threadQueues = new ConcurrentHashMap<>();

    private final String threadName;

    public BlockingQueuesLearning(String threadName) {
        this.threadName = threadName;
        threadQueues.putIfAbsent(threadName, new LinkedBlockingQueue<>());
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new BlockingQueuesLearning("a")).start();
        new Thread(new BlockingQueuesLearning("b")).start();
        new Thread(new BlockingQueuesLearning("c")).start();
        threadQueues.get("a").put("Hello A!");
        threadQueues.get("b").put("Hello B!");
        threadQueues.get("c").put("Hello C!");
        System.out.println(threadQueues.get("a").peek() + "from sout");
    }


    @Override
    public void run() {
        BlockingQueue<String> myQueue = threadQueues.get(threadName);

        try {
            while (true) {
                String message = myQueue.take();  // blocks until message is available
                System.out.println("Thread " + threadName + " received: " + message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
