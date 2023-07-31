package garpin.taskExecutor.storage;

import garpin.taskExecutor.controllers.Task;
import garpin.taskExecutor.controllers.TaskCollection;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * An (ordered) queue of tasks for consumption in FIFO order
 */
public class TaskQueue implements TaskCollection {

    LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    /**
     * {@inheritDoc}
     * Adds a task to the end of the queue
     *
     * @param task
     */
    @Override
    public synchronized void addTask(Task task) {
        queue.add(task);
    }

    /**
     * {@inheritDoc}
     * Removes and returns the "oldest" task in the queue
     *
     * @return - the removed task
     */
    @Override
    public synchronized Task removeTask() {
        return queue.poll();
    }
}
