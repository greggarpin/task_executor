package garpin.taskExecutor.controllers;

import java.security.InvalidParameterException;
import java.util.concurrent.CancellationException;

/**
 * Abstract work item to be performed by the system. Generic management occurs in this class. Implementing tasks are
 * responsible for tracking their own arguments and implementations.
 */
public abstract class Task {

    private TaskState state = TaskState.PENDING;
    private String creator = "";
    private String results = "";

    public enum TaskState {
        PENDING,
        RUNNING,
        CANCELLING,
        CANCELLED,
        COMPLETED,
        ERROR
    }

    /**
     * Executes the task. Invokes task validation, state management and exception-case results storage, but defers
     * to abstract 'runTask()' method to perform the actual task duties
     */
    public void startTask() {
        setState(TaskState.RUNNING);

        try {
            validateTask();

            runTask();

            setState(TaskState.COMPLETED);

        } catch (CancellationException cancEx) {

            setState(TaskState.CANCELLED);
            setResults("Task cancelled before completion");

        } catch (Exception ex) {

            setState(TaskState.ERROR);
            setResults("Error executing task: " + ex.getMessage());
        }
    }

    /**
     * Placeholder method to perform task execution.
     * Subclasses are responsible for setting results in non-exception cases (via 'setResults()' method)
     * Subclasses may throw any type of Exception to fail a task.
     * Subclasses may throw a CancellationException in order to prematurely abort a task (e.g., when state has been set
     * to 'CANCELLING').
     * CancellationException will allow task to be marked as 'CANCELLED', all other exceptions will cause Task to be
     * marked as 'ERROR'
     *
     * @throws Exception - for any relevant reason
     */
    protected abstract void runTask() throws Exception;

    /**
     * Returns a String indicating the Task's type for output to users
     *
     * @return - a user-readable String representing the task type
     */
    protected abstract String getTaskType();

    /**
     * Verifies that all member fields are correctly populated in the Task and throws an exception if they are not
     * Subclasses should extend this functionality to validate subtask-specific arguments
     *
     * @throws Exception - if any member field is not correctly populated
     */
    public void validateTask() throws Exception {
        if (creator == null || creator.isEmpty()) {
            throw new InvalidParameterException("Missing user");
        }
    }

    /**
     * Returns a String representing the output of the Task execution. In error conditions, this may provide
     * more information about the failure mode
     *
     * @return - String representation of Task output
     */
    public String getResults() {
        return results;
    }

    /**
     * Sets a String value indicating the output of the executed Task
     *
     * @param value - String indicating the Task's output
     */
    protected void setResults(String value) {
        results = value;
    }

    /**
     * The Task's current state (PENDING, RUNNING, COMPLETED, etc.)
     *
     * @return the current state of the task
     */
    public synchronized TaskState getState() {
        return state;
    }

    /**
     * Sets the current state of the task (PENDING, RUNNING, CANCELLING, etc.)
     * Setting the state to CANCELLING provides subclasses permission to prematurely abort Task execution
     *
     * @param newState - the desired TaskState for this task
     */
    public synchronized void setState(TaskState newState) {
        state = newState;
    }

    /**
     * Returns a String representation of the user that created this Task
     *
     * @return - a String representation of the user that created this Task
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the name of the user who initiated this Task
     * (Note that a more complete implementation could remove this function and allow the Task to read from a proper
     * User management system)
     *
     * @param newUser - String name of the user who initiated this Task
     */
    public void setCreator(String newUser) {
        if (newUser == null) {
            newUser = "";
        }

        creator = newUser;
    }

    /**
     * Creates a user-readable String representation of this Task
     * (Note that a more complete implementation would likely want to separate the output format from this class as
     * a UI concern)
     *
     * @return - a String representation of this Task
     */
    @Override
    public String toString() {
        return (new StringBuilder())
                .append("Type: ").append(getTaskType()).append("\n")
                .append("User: ").append(creator).append("\n")
                .append("State: ").append(state).append("\n")
                .append("Result: ").append(results).append("\n")
                .toString();
    }
}
