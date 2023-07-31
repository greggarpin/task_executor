package garpin.taskExecutor.controllers;

import garpin.taskExecutor.storage.TaskQueue;

import java.security.InvalidParameterException;
import java.util.Vector;

/**
 * Manages Task serialization and asynchronous execution.
 */
public class TaskController {

    // For simplicity in this exercise, we maintain only two "known" users
    // Ideally, this would be managed in a separate module
    public final String USER_STANDARD = "user";
    public final String USER_ADMIN = "admin";

    private String user = USER_STANDARD;

    private TaskCollection tasks = new TaskQueue();
    private TaskExecutor executor = new TaskExecutor(tasks);

    private static TaskController instance = new TaskController();

    /**
     * Returns a singleton instance of this class
     *
     * @return - singleton instance of this class
     */
    public static TaskController getInstance() {
        return instance;
    }

    // Private constructor for singleton pattern
    private TaskController() {
    }

    /**
     * Creates and starts a Thread running TaskExecutor asynchronously
     *
     * @throws Exception
     */
    public void startExecutorProcess() throws Exception {
        (new Thread(executor)).start();
    }

    /**
     * Signals system to shut down asynchronous processing. (Notifies threaded process)
     */
    public void shutdownExecutorProcess() {
        executor.shutdown();
    }

    /**
     * Enables Task execution for processing
     *
     * @throws Exception - if current user does not have sufficient permission
     */
    public void enableExecutor() throws Exception {
        verifyIsAdmin();

        executor.enable();
    }

    /**
     * Blocks Task execution from processin any new tasks
     *
     * @throws Exception - if current user does not have sufficient permission
     */
    public void disableExecutor() throws Exception {
        verifyIsAdmin();

        executor.disable();
    }

    /**
     * Validates given Task and adds it to the pending queue
     *
     * @param task - Task to be scheduled
     * @throws Exception - if task execution is not enabled or Task is not valid
     */
    public void scheduleTask(Task task) throws Exception {

        // Relying on the executor's state here is somewhat awkward, however, the alternative is to push this logic
        //  into the Executor which increases coupling and puts more responsibility on that class. Could also have
        //  tracked a separate 'enabled' boolean in this class, but that creates duplication of knowledge
        if (!executor.isEnabled()) {
            throw new Exception("Task scheduler is not enabled");
        }

        task.setCreator(user);

        task.validateTask();

        tasks.addTask(task);
    }

    /**
     * Flags a task to be cancelled if it is currently executing and is not already in a terminal state.
     * Note that this is just *marking* a Task for cancellation. The Task implementation may not respond to
     * the cancel request immediately or ever
     *
     * @throws Exception - if current user does not have sufficient permission
     */
    public void requestCancelCurrentTask() throws Exception {
        verifyIsAdmin();

        Task currentTask = getCurrentTask();

        if (currentTask == null) {
            return;
        }

        // If task not in a terminal state, mark for cancellation (otherwise, there is nothing to cancel)
        switch (currentTask.getState()) {
            case PENDING:
            case RUNNING:
                currentTask.setState(Task.TaskState.CANCELLING);
                break;
        }
    }

    /**
     * Returns a String summary of the currently executing Task. If no Task is currently being executed, a placeholder
     * String is returned
     *
     * @return - String summary of any currently executing Task or a placeholder
     * @throws Exception - if current user does not have sufficient permission
     */
    public String fetchCurrentTaskInfo() throws Exception {
        verifyIsAdmin();

        Task currentTask = getCurrentTask();

        if (currentTask == null) {
            return "<No task>";
        }

        return currentTask.toString();
    }

    /**
     * Returns any currently executing Task from the asynchronously running TaskExecutor.
     * May be null.
     *
     * @return - any currently executing Task from the asynchronously running TaskExecutor
     */
    private synchronized Task getCurrentTask() {

        return executor.getCurrentTask();
    }

    /**
     * Creates and returns a String summary of all previously completed Tasks
     * Note that this is not a very robust solution and could be greatly expanded
     *
     * @return - A String summary of all completed Tasks
     */
    public String fetchCompletedTasksInfo() {
        Vector<Task> tasks = executor.getCompletedTasks();

        StringBuilder output = new StringBuilder();

        final String delimiter = "--------------------\n";

        for (Task t : tasks) {
            output.append(delimiter);
            output.append(t.toString());
            output.append("\n");
        }

        output.append(delimiter);

        return output.toString();
    }

    /**
     * Sets the current user of the system to be used for tracking Task creators and whether users have permission to
     * perform activities.
     * Note that for this project, the implementation only accepts two hardcoded user names and does no validation at
     * all. In a production environment, this must be replaced by a robust system.
     *
     * @param newUser
     * @throws Exception - if the given user is unknown
     */
    public void setUser(String newUser) throws Exception {
        if (newUser.compareToIgnoreCase(USER_ADMIN) != 0 &&
                newUser.compareToIgnoreCase(USER_STANDARD) != 0) {

            throw new InvalidParameterException("Unknown user: " + newUser);
        }

        user = newUser;
    }

    /**
     * Verifies that the current user is an administrator. If not, an execption is thrown.
     *
     * @throws Exception if the current user is not an administrator
     */
    private void verifyIsAdmin() throws Exception {
        if (user.compareToIgnoreCase(USER_ADMIN) != 0) {
            throw new InvalidParameterException("Admin user required");
        }
    }
}
