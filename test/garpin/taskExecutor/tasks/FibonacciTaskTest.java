package garpin.taskExecutor.tasks;

import garpin.taskExecutor.controllers.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTaskTest extends TaskTest {

    @Test
    public void validateTask() {
        FibonacciTask t = new FibonacciTask();
        t.setCreator("testuser");

        assertValidationFails(t, "Uninitialized fibonacci index");

        t.setFibonacciIndex(-1);
        assertValidationFails(t, "Negative fibonacci index");

        t.setFibonacciIndex(0);
        assertValidationFails(t, "Zero fibonacci index");

        t.setFibonacciIndex(10);
        assertValidationSucceeds(t);
    }

    @Test
    public void runTask() {
        FibonacciTask t = new FibonacciTask();
        t.setCreator("testUser");
        t.setFibonacciIndex(6);

        try {
            t.runTask();
        } catch (Exception e) {
            fail("Unexpected exception running fibonacci task: " + e.getMessage());
        }

        assertEquals("" + 8, t.getResults());
    }

    @Override
    public void getTaskType() {
        FibonacciTask t = new FibonacciTask();
        assertEquals("Fibonacci", t.getTaskType());
    }

    @Override
    protected Task createTestTaskInstance() {
        return new FibonacciTask();
    }
}