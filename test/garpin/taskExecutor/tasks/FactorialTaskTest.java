package garpin.taskExecutor.tasks;

import garpin.taskExecutor.controllers.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTaskTest extends TaskTest {

    @Test
    public void validateTask() {
        FactorialTask t = new FactorialTask();
        t.setCreator("testuser");

        assertValidationFails(t, "Uninitialized factorial base");

        t.setFactorialBase(-1);
        assertValidationFails(t, "Negative factorial base");

        t.setFactorialBase(0);
        assertValidationFails(t, "Zero factorial base");

        t.setFactorialBase(10);
        assertValidationSucceeds(t);
    }

    @Test
    public void runTask() {
        FactorialTask t = new FactorialTask();
        t.setCreator("testUser");
        t.setFactorialBase(5);

        try {
            t.runTask();
        } catch (Exception e) {
            fail("Unexpected exception running factorial task: " + e.getMessage());
        }

        assertEquals("" + 5 * 4 * 3 * 2 * 1, t.getResults());
    }

    @Override
    public void getTaskType() {
        FactorialTask t = new FactorialTask();
        assertEquals("Factorial", t.getTaskType());
    }

    @Override
    protected Task createTestTaskInstance() {
        return new FactorialTask();
    }
}