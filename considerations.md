# Relevant considerations during implementation

## Use of Java 8 version
Java has had many newer versions since version 8. However, Java 8 is still in long term support (LTS) for several more
years making it a viable alternative.
Using an older version of Java seemed prudent to reduce the chances that a user's machine
would have an incompatible JRE.
Given the nature of the project, this seemed like a reasonable tradeoff

## Multi-threaded application
The nature of this project's requirements strongly enforced asynchronous processing of some type. In a full blown product,
it would likely have made sense to invest in a multi-process implementation where a single background service is continually
running and one or more instances of a management tool could schedule, view and manage tasks.
However, that implementation quickly becomes complex to deploy and forces external serialization of tasks (and possibly
networking) to manage inter-process communication.
For simplicity and to keep the project in scope, we adopted a multi-threaded approach.

## Liberal use of base Exceptions
For public methods that throw exceptions, we preferred throwing the base Exception class rather than a more specific
Exception. This was done to reduce coupling between implementations and consumers.
For example, if an implementation changes to introduce new types of exceptions (perhaps we migrate from an in-memory task
queue to an external message queue), the method signatures would need to change which would cause the consumer
implementations to change.
To mitigate this excessive need for change, we erred on the side of declaring generic exceptions.

## Declaration of Task base class in controllers package
Given that there is a dedicated package for _tasks_, it may seem counterintuitive that the abstract
base is declared in the _controllers_ package.
This was done intentionally as a way to control package dependencies following the Dependency Inversion Principle.
The intent is to avoid having the _controllers_ package directly dependent on the _tasks_ package since new tasks may be
defined later. The approach declares a generic Task concept in the _controllers_ package which allows _controllers_ to
operate without any direct knowledge of the existence of a _tasks_ package.

## Task results as Strings
Given that there are only two concrete Tasks and bother return an integer, it may seem that the results of the task should
also be an integer. However, the fact that the return types align is little more than a coincidence. It is possible or even
likely that future tasks will have results in the form of an float, an array or even a string. Given that consideration,
tracking results generically as integers would limit future expansion. Instead, we represent generic results as a String
as that is an easily consumed format and gives the implementations more control.

## Pending Tasks are in a collection, but complete Tasks are in a Vector
This could arguably have been done in other ways, however the discrepancy is not accidental. Pending tasks are stored in
a TaskCollection. Today, that happens to be backed by an in-memory queue. However, an alternative implementation could
decide to store pending tasks elsewhere (e.g., external event queues or even in another non-FIFO system).
We would not want that type of change to necessarily affect the storage of completed tasks which may have different
requirements. For example, if we suddenly move pending tasks to a system like AWS SQS, we do not want to be forced to also
update our completed task storage to AWS SQS. So, we keep them with distinct storage approaches.
In this case, completed tasks are stored in a simple Vector (but the argument could be made for more encapsulation around
their serialization as well)

## CLI implementation
In order to effectively demonstrate and test this application, it was necessary to have some type of user interface running.
The easiest approach was to write a small CLI implementation.
The implementation strives to be useful and clear, but is not a great example of usability.
The ultimate intent is that the CLI implementation is discardable and could be replaced with a web application and backend
service(s) available via HTTP protocol.
(Note that there are no dependencies on the CLI from other parts of the system to facilitate this type of change in the
future)

## Integration of user management with TaskController
This was done to keep the scope reasonable. In a production environment, user management would be robust and secure and
sufficiently decoupled from Task management. However, that seemed outside the scope of this project, so we opted to go
with an imperfect approach tracking hardcoded usernames with no credential validation
