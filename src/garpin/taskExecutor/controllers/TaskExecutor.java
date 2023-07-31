package garpin.taskExecutor.controllers;

import java.util.Vector;

/**
 * Responsible for executing tasks from an external task queue. Supports being run in a separate thread
 */
public class TaskExecutor implements Runnable {

    private boolean enabled = true;
    private boolean shuttingDown = false;
    private TaskCollection pendingTasks = null;
    private Vector<Task> completedTasks = new Vector<>();
    private Task currentTask = null;

    /**
     * Constructor - initializes TaskExecutor with a reference to a collection serving tasks to be executed
     *
     * @param tasks - TaskCollection tracking Tasks to be executed. (Note that this is not owned by TaskExecutor,
     *              merely consumed)
     */
    public TaskExecutor(TaskCollection tasks) {
        pendingTasks = tasks;
    }

    /**
     * Returns the currently executing Task if there is one, null otherwise
     *
     * @return - currently executing Task or null
     */
    public Task getCurrentTask() {
        return currentTask;
    }

    /**
     * Signals the TaskExecutor to shut down (i.e., to stop executing tasks and to exit the processing loop)
     */
    public void shutdown() {
        shuttingDown = true;
    }

    /**
     * Enables the TaskExecutor allowing it to process tasks
     */
    public synchronized void enable() {
        enabled = true;
    }

    /**
     * Disables the TaskExecutor preventing it from processing tasks. (Note that the processing loop is still active,
     * but does not read or execute any new tasks)
     */
    public synchronized void disable() {
        enabled = false;
    }

    /**
     * Returns whether the TaskExecutor is enabled (i.e., actively processing tasks)
     *
     * @return whether the TaskExecutor is enabled (i.e., actively processing tasks)
     */
    public synchronized boolean isEnabled() {
        return enabled;
    }

    /**
     * {@inheritDoc}
     * Creates a loop that executes tasks from the pending queue until TaskExecutor is marked for shutdown.
     * Even when marked for shutdown, any active Task will continue to be processed (although Task may terminate itself
     * early if desired)
     */
    @Override
    public void run() {
        while (!shuttingDown) {
            processNextTask();

            // Sleep briefly to help make queue draining visible for this exercise. In a production environment, we may
            // still want to sleep when pending queue is empty to avoid excessive thrashing
            try {
                final long sleepMilliseconds = 1000;
                Thread.sleep(sleepMilliseconds);

            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Retrieves and executes a single task from the pending queue
     */
    protected void processNextTask() {

        if (!isEnabled()) {
            return;
        }

        currentTask = pendingTasks.removeTask();

        if (currentTask == null) {
            return;
        }

        currentTask.startTask();

        completedTasks.add(currentTask);
    }

    /**
     * Returns a copy of all completed Task records
     *
     * @return - Vector containing all completed Task records
     */
    public Vector<Task> getCompletedTasks() {

        return (Vector<Task>) completedTasks.clone();
    }
}
