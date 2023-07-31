package garpin.taskExecutor.tasks;

import garpin.taskExecutor.controllers.Task;

import java.util.concurrent.CancellationException;

/**
 * Implements a Task for calculating the factorial of a given number (e.g., (factorialBase)! )
 */
public class FactorialTask extends Task {

    private int factorialBase = 0;

    /**
     * Sets the number of which to take the factorial.
     *
     * @param base - the number to be used for the factorial
     */
    public void setFactorialBase(int base) {
        factorialBase = base;
    }

    /**
     * {@inheritDoc}
     * Calculates the factorial and sets the results in the parent class
     *
     * @throws Exception - if the calculateFactorial implementation throws an exception
     */
    @Override
    protected void runTask() throws Exception {

        int output = calculateFactorial(factorialBase);

        setResults("" + output);
    }

    /**
     * Recursively calculates the factorial value of the given number
     *
     * @param n - the number for which to calculate a factorial
     * @return - the factorial of the given 'n' argument
     * @throws CancellationException - if the Task has been marked for cancellation
     */
    private int calculateFactorial(int n) throws CancellationException {

        if (getState() == TaskState.CANCELLING) {
            throw new CancellationException();
        }

        // Using a recursive solution for simplicity and to allow exercising the cancellation mechanism. An iterative
        // solution would be safer and more efficient in a production environment
        if (n == 1) {
            return 1;
        }

        return n * calculateFactorial(n - 1);
    }

    /**
     * {@inheritDoc}
     * Validates that this Task is sufficiently populated to be executed. Leverages parent's validation and checks
     * that the factorial base is an appropriate value
     *
     * @throws Exception - if any part of the Task does not have a valid value
     */
    @Override
    public void validateTask() throws Exception {
        super.validateTask();

        if (factorialBase <= 0) {
            throw new IndexOutOfBoundsException("Invalid value (" + factorialBase + ") for factorial calculation.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return - "Factorial"
     */
    @Override
    protected String getTaskType() {
        return "Factorial";
    }
}
