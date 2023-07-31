package garpin.taskExecutor.tasks;

import garpin.taskExecutor.controllers.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskTest {

    @Test
    public abstract void getTaskType();

    protected abstract Task createTestTaskInstance();

    @Test
    void setState() {
        Task t = createTestTaskInstance();

        for (Task.TaskState state : Task.TaskState.values()) {
            t.setState(state);
            assertEquals(state, t.getState(), "Unexpected state assigned to task");
        }
    }

    @Test
    void setUser() {
        Task t = createTestTaskInstance();
        String testUser = "testUserName";

        t.setCreator(testUser);
        assertEquals(testUser, t.getCreator(), "Unexpected user assigned to task");
    }

    protected static void assertValidationFails(Task task, String failureMessage) {
        try {
            task.validateTask();
            fail("Expected validation exception: " + failureMessage);

        } catch (Exception e) {
        }
    }

    protected static void assertValidationSucceeds(Task task) {
        try {
            task.validateTask();

        } catch (Exception e) {
            fail("Did not expect validation exception: " + e.getMessage());
        }
    }
}