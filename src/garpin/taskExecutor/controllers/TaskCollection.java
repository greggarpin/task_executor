package garpin.taskExecutor.controllers;

/**
 * Encapsulation around task storage. This interface allows us to know that tasks can be added and removed to a collection
 * without knowledge of how or where the collection is tracked or ordered. E.g., implementations may choose to store tasks
 * in FIFO queues, priority queues, external SQS queues, etc.
 */
public interface TaskCollection {

    /**
     * Adds a Task to the collection
     *
     * @param task - the Task to be added
     */
    void addTask(Task task);

    /**
     * Removes a Task from the collection
     *
     * @return - the removed Task
     */
    Task removeTask();
}
