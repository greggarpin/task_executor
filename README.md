# task_executor project

## Design considerations
See [considerations.md](considerations.md)

## Usage
### Basic
1. Download [task_executor.jar](https://github.com/greggarpin/task_executor/blob/main/bin/task_executor.jar)
2. Start application:
    `java -jar task_executor.jar`
3. Follow command prompts
### Examples
#### Schedule a task to find the 6th Fibonacci number
1. At the prompt, enter **5** to schedule a new task
2. When prompted, choose **1** for Fibonacci task
3. Enter **6** to request the 6th Fibonacci number
4. Task is scheduled
```
Enter a command:
        1: Quit
        2: Change user
        3: Enable executor
        4: Disable executor
        5: Schedule new task
        6: Cancel current task
        7: View current task
        8: View all completed tasks
5

Enter a task type:
        1: Fibonacci
        2: Factorial
1

Enter desired Fibonacci index (1 - n):
6

Fibonacci task scheduled
```
#### View the results of all completed tasks
1. At the prompt, enter **8** to view the results of previously completed taks
```
Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
8

--------------------
Type: Fibonacci
User: user
State: COMPLETED
Result: 8

--------------------
Type: Factorial
User: user
State: COMPLETED
Result: 5040

--------------------
Type: Factorial
User: user
State: COMPLETED
Result: 24

--------------------
```
#### To disable the task executor
(Note that only an 'admin' user can disable the task executor)
1. At the prompt, enter **2** to change the user
2. When prompted, enter the username **_admin_**
3. At the original prompt, enter **4** to disable the task executor

```
Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
2

Enter user name (hint for this exercise: Use either "admin" or "user")
admin

User is now admin

(Enter to continue)

Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
4

Executor process is disabled
```
#### To view a currently running task
1. At the prompt, enter **2** to change the user
2. When prompted, enter the username **_admin_**
3. At the original prompt, enter **5** to schedule a new task
4. Schedule a long running task (otherwise the task will complete before it can be viewed)
   1. Enter **1** to choose a Fibonacci task
   2. Enter **50** to request the 50th Fibonacci number
5. At the original prompt, choose **7** to view the current task 
```
Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
2

Enter user name (hint for this exercise: Use either "admin" or "user")
admin

User is now admin

(Enter to continue)

Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
5

Enter a task type:
	1: Fibonacci
	2: Factorial
1

Enter desired Fibonacci index (1 - n):
50

Fibonacci task scheduled

(Enter to continue)

Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
7

Type: Fibonacci
User: admin
State: RUNNING
Result: 
```
#### To cancel a currently running task
1. At the prompt, enter **2** to change the user
2. When prompted, enter the username **_admin_**
3. At the original prompt, enter **5** to schedule a new task
4. Schedule a long running task (otherwise the task will complete before it can be viewed)
    1. Enter **1** to choose a Fibonacci task
    2. Enter **50** to request the 50th Fibonacci number
5. At the original prompt, choose **6** to cancel the current task
6. At the original prompt, choose **8** to view completed tasks. Notice that Fibonacci task has been cancelled. (Cancellation is not synchronous and may take a moment)
```
Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
2

Enter user name (hint for this exercise: Use either "admin" or "user")
admin

User is now admin

(Enter to continue)

Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
5

Enter a task type:
	1: Fibonacci
	2: Factorial
1

Enter desired Fibonacci index (1 - n):
50

Fibonacci task scheduled

(Enter to continue)

Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
6

Done

(Enter to continue)

Enter a command:
	1: Quit
	2: Change user
	3: Enable executor
	4: Disable executor
	5: Schedule new task
	6: Cancel current task
	7: View current task
	8: View all completed tasks
8

--------------------
Type: Fibonacci
User: admin
State: CANCELLED
Result: Task cancelled before completion

--------------------
```
## Instructions (from QuEra)
Title: Task Executor Component Design and Implementation

Task Description:

We would like to see the development and testing of a Task Executor component. This component should provide task execution management with a focus on concurrency control and result retrieval.

Specific requirements include:

1. **Task Submission**: Users should be able to submit a task for execution. The available tasks will be predefined; initially, these will include the calculation of a factorial and a Fibonacci number. Users will select the type of task and provide necessary parameters.

2. **Task Execution**: The system should be designed to execute one task at a time. Users should not be able to submit a new task while another is running.

3. **Result Retrieval**: Users should be able to retrieve results of completed tasks upon request. To simplify, assume an in-memory storage for these results, and we will not focus on data retention at this point.

4. **Admin Controls**: Admin users should have the following capabilities:

  - Retrieve details of the currently running task, including its type and user.

  - Cancel the currently running task.

  - Enable or disable the Task Executor. When the Executor is disabled, it should not accept new tasks from users.

5. **Design and Structure**: The Task Executor component should be akin to a library, designed in a way that is isolated from the “external layer”. Therefore, HTTP endpoints or a main method aren’t required at this stage, but it should be easy to hook them up later.

6. **Demonstration**: we want some code (think unit tests suite) to demonstrating functionality of solution,

corner cases, etc.

7. **Documentation**: Any additional documentation, such as design diagrams or detailed code explanations, that you would typically use to present design-code to the team would be appreciated.

