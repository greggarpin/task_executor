package garpin.taskExecutor.userinterface;

import garpin.taskExecutor.controllers.TaskController;
import garpin.taskExecutor.tasks.FactorialTask;
import garpin.taskExecutor.tasks.FibonacciTask;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Rudimentary CLI application for Task management demonstration
 */
public class CLI {

    private static final int CMD_DONE = 1;
    private static final int CMD_SET_USER = 2;
    private static final int CMD_ENABLE_EXECUTOR = 3;
    private static final int CMD_DISABLE_EXECUTOR = 4;
    private static final int CMD_SCHEDULE_TASK = 5;
    private static final int CMD_CANCEL_CURRENT_TASK = 6;
    private static final int CMD_READ_CURRENT_TASK = 7;
    private static final int CMD_VIEW_COMPLETED_TASKS = 8;

    private static final int CMD_TASK_TYPE_FIBONACCI = 1;
    private static final int CMD_TASK_TYPE_FACTORIAL = 2;

    /**
     * Entry point of application. Starts asynchronous task execution and begins processing CLI commands
     *
     * @param args - not used
     */
    public static void main(String[] args) {

        startExecutor();
        processCommands();

        stopExecutor();
    }

    /**
     * Launches asynchronous Task Execution
     */
    private static void startExecutor() {
        try {
            TaskController.getInstance().startExecutorProcess();
        } catch (Exception ex) {
            reportExceptionToUser("Failed to start task executor", ex);
        }
    }

    /**
     * Shuts down asynchronous task execution process
     */
    private static void stopExecutor() {
        TaskController.getInstance().shutdownExecutorProcess();
    }

    /**
     * Solicits and processes user input from the command line in a loop until user ends program
     */
    private static void processCommands() {
        int nextCommand = 0;

        while (nextCommand != CMD_DONE) {
            nextCommand = getUserCommand();

            switch (nextCommand) {
                case CMD_DONE: {
                    break;
                }
                case CMD_SET_USER: {
                    processCommandSetUser();
                    break;
                }
                case CMD_ENABLE_EXECUTOR: {
                    processCommandEnableExecutor();
                    break;
                }
                case CMD_DISABLE_EXECUTOR: {
                    processCommandDisableExecutor();
                    break;
                }
                case CMD_SCHEDULE_TASK: {
                    processCommandScheduleTask();
                    break;
                }
                case CMD_CANCEL_CURRENT_TASK: {
                    processCommandCancelCurrentTask();
                    break;
                }
                case CMD_READ_CURRENT_TASK: {
                    processCommandReadCurrentTask();
                    break;
                }
                case CMD_VIEW_COMPLETED_TASKS: {
                    processCommandViewCompletedTasks();
                    break;
                }
                default: {
                    notifyUser("Invalid value");
                    awaitUserPromptToContinue();
                    break;
                }
            }
        }
    }

    /**
     * Outputs a given message to the user
     *
     * @param message - String message to be displayed
     */
    private static void notifyUser(String message) {
        System.out.println(message);
    }

    /**
     * Prompts user for next command
     *
     * @return - integer representation of user's command
     */
    private static int getUserCommand() {

        Scanner input = new Scanner(System.in);

        notifyUser("Enter a command:");
        notifyUser("\t1: Quit");
        notifyUser("\t2: Change user");
        notifyUser("\t3: Enable executor");
        notifyUser("\t4: Disable executor");
        notifyUser("\t5: Schedule new task");
        notifyUser("\t6: Cancel current task");
        notifyUser("\t7: View current task");
        notifyUser("\t8: View all completed tasks");

        try {
            return input.nextInt();

        } catch (InputMismatchException exception) {
            notifyUser("Invalid value");
            awaitUserPromptToContinue();

            return getUserCommand();
        }
    }

    /**
     * Processes a user request to set the user - prompts for new username and updates accordingly
     */
    private static void processCommandSetUser() {
        Scanner input = new Scanner(System.in);

        notifyUser("Enter user name (hint for this exercise: Use either \"admin\" or \"user\")");
        String username = input.next();

        try {
            TaskController.getInstance().setUser(username);
            notifyUser("User is now " + username);
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Unable to accept user name", exception);
        }
    }

    /**
     * Processes a user request to enable the TaskExecutor
     */
    private static void processCommandEnableExecutor() {
        try {
            TaskController.getInstance().enableExecutor();

            notifyUser("Executor process is enabled");
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Could not enable executor process", exception);
        }
    }

    /**
     * Processes a user request to disable the TaskExecutor
     */
    private static void processCommandDisableExecutor() {
        try {
            TaskController.getInstance().disableExecutor();

            notifyUser("Executor process is disabled");
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Could not disable executor process", exception);
        }
    }

    /**
     * Begins processing a user request to schedule a new task by prompting user for task type and delegating accordingly
     */
    private static void processCommandScheduleTask() {
        Scanner input = new Scanner(System.in);

        notifyUser("Enter a task type:");
        notifyUser("\t1: Fibonacci");
        notifyUser("\t2: Factorial");

        try {
            int type = input.nextInt();

            switch (type) {
                case CMD_TASK_TYPE_FIBONACCI: {
                    processCommandScheduleFibonacciTask();
                    break;
                }
                case CMD_TASK_TYPE_FACTORIAL: {
                    processCommandScheduleFactorialTask();
                    break;
                }
                default: {
                    notifyUser("Invalid task type");
                    awaitUserPromptToContinue();
                    break;
                }
            }
        } catch (Exception exception) {
            reportExceptionToUser("Task type not accepted", exception);
        }
    }

    /**
     * Completes scheduling of a Fibonacci task by prompting user for required arguments, creating the task and adding
     * it to the queue
     */
    private static void processCommandScheduleFibonacciTask() {
        Scanner input = new Scanner(System.in);

        notifyUser("Enter desired Fibonacci index (1 - n):");
        try {
            int value = input.nextInt();

            FibonacciTask task = new FibonacciTask();
            task.setFibonacciIndex(value);
            TaskController.getInstance().scheduleTask(task);

            notifyUser("Fibonacci task scheduled");
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Unable to schedule task", exception);
        }
    }

    /**
     * Completes scheduling of a Factorial task by prompting user for required arguments, creating the task and adding
     * it to the queue
     */
    private static void processCommandScheduleFactorialTask() {
        Scanner input = new Scanner(System.in);

        notifyUser("Enter desired Factorial base number (1 - n):");
        try {
            int value = input.nextInt();

            FactorialTask task = new FactorialTask();
            task.setFactorialBase(value);
            TaskController.getInstance().scheduleTask(task);

            notifyUser("Factorial task scheduled");
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Unable to schedule task", exception);
        }
    }

    /**
     * Processes user request to cancel any currently running task
     */
    private static void processCommandCancelCurrentTask() {
        try {
            TaskController.getInstance().requestCancelCurrentTask();

            notifyUser("Done");
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Could not cancel current task", exception);
        }
    }

    /**
     * Processes a user's request to view the currently running task
     */
    private static void processCommandReadCurrentTask() {
        try {
            String info = TaskController.getInstance().fetchCurrentTaskInfo();

            notifyUser(info);
            awaitUserPromptToContinue();

        } catch (Exception exception) {
            reportExceptionToUser("Could not fetch current task info", exception);
        }
    }

    /**
     * Processes a user's request to view all completed tasks
     */
    private static void processCommandViewCompletedTasks() {

        String info = TaskController.getInstance().fetchCompletedTasksInfo();

        notifyUser(info);
        awaitUserPromptToContinue();
    }

    /**
     * Provides a notification to the user of an exception along with relevant details
     *
     * @param message   - String message giving context to the exception
     * @param exception - Exception encountered by the system
     */
    private static void reportExceptionToUser(String message, Exception exception) {
        // Note that in a production environment, blindly dumping exceptions to users is poor usability
        // and a potential security hole. Doing it here for expediency.
        notifyUser(message);
        notifyUser("Exception: " + exception.getMessage());

        awaitUserPromptToContinue();
    }

    /**
     * Waits for user to press <Enter> key before continuing execution
     */
    private static void awaitUserPromptToContinue() {
        Scanner input = new Scanner(System.in);

        notifyUser("\n(Enter to continue)");
        input.nextLine();
    }
}