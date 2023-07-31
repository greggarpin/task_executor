package garpin.taskExecutor.tasks;

import garpin.taskExecutor.controllers.Task;

import java.util.concurrent.CancellationException;

/**
 * Implements a Task for calculating the n-th Fibonacci number
 */
public class FibonacciTask extends Task {

    private int fibIndex = 0;

    /**
     * Sets the index of the desired Fibonacci number
     *
     * @param index - the index of the desired Fibonacci number
     */
    public void setFibonacciIndex(int index) {
        fibIndex = index;
    }

    /**
     * {@inheritDoc}
     * Calculates the Fibonacci number and sets the results in the parent class
     *
     * @throws Exception - if the getNthFibonacciNumber implementation throws an exception
     */
    @Override
    protected void runTask() throws Exception {

        int output = getNthFibonacciNumber(fibIndex);

        setResults("" + output);
    }

    /**
     * Recursively calculates the given Fibonacci number
     *
     * @param n - the index of the desired Fibonacci number
     * @return - the nth Fibonacci number
     * @throws CancellationException - if the Task has been marked for cancellation
     */
    private int getNthFibonacciNumber(int n) throws CancellationException {

        if (getState() == TaskState.CANCELLING) {
            throw new CancellationException();
        }

        // Using a recursive solution for simplicity and to allow exercising the cancellation mechanism. An iterative
        // solution would be safer and more efficient in a production environment
        if (n == 1 || n == 2) {
            return 1;
        }

        return getNthFibonacciNumber(n - 1) + getNthFibonacciNumber(n - 2);
    }

    /**
     * {@inheritDoc}
     * Validates that this Task is sufficiently populated to be executed. Leverages parent's validation and checks
     * that the Fibonacci index is an appropriate (i.e., positive integer) value
     *
     * @throws Exception - if any part of the Task does not have a valid value
     */
    @Override
    public void validateTask() throws Exception {
        super.validateTask();

        if (fibIndex <= 0) {
            throw new IndexOutOfBoundsException("Invalid index (" + fibIndex + ") for Fibonacci sequence.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return - "Fibonacci"
     */
    @Override
    protected String getTaskType() {
        return "Fibonacci";
    }
}
