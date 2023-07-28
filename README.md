# task_executor
## Instructions
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

