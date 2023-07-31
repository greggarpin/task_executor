package garpin.taskExecutor.storage;

import garpin.taskExecutor.controllers.Task;
import garpin.taskExecutor.tasks.FactorialTask;
import garpin.taskExecutor.tasks.FibonacciTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskQueueTest {

    @Test
    void addRemoveTask() {
        Task testTasks[] = {
                new FibonacciTask(),
                new FibonacciTask(),
                new FactorialTask()
        };

        TaskQueue queue = new TaskQueue();

        assertSame(null, queue.removeTask(), "Expected null from empty queue");

        for (int i = 0; i < testTasks.length; ++i) {
            queue.addTask(testTasks[i]);
        }

        for (int i = 0; i < testTasks.length; ++i) {
            Task retrievedTask = queue.removeTask();

            assertSame(testTasks[i], retrievedTask, "Unexpected task found in queue");
        }

        assertSame(null, queue.removeTask(), "Expected null from empty queue");
    }
}