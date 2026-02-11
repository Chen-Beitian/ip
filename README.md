# Momo User Guide
Momo is a desktop task manager for users who prefer typing commands.  
It allows you to manage todos, deadlines, and events efficiently using a command-based interface.

---
## Features
### Adding a Todo
Adds a task without date or time.
Format:
todo DESCRIPTION
Example:
todo read book

---
### Adding a Deadline
Adds a task with a due date.
Format:
deadline DESCRIPTION /by yyyy-mm-dd
Example:
deadline return book /by 2026-02-15

---
### Adding an Event
Adds a task with start and end date/time.
Format:
event DESCRIPTION /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm
Example:
event project meeting /from 2026-02-15 1400 /to 2026-02-15 1600

---
### Listing Tasks
Displays all tasks.
Format:
list

---
### Marking a Task
Marks a task as done.
Format:
mark INDEX

---
### Unmarking a Task
Marks a task as not done.
Format:
unmark INDEX

---
### Deleting a Task
Deletes a task.
Format:
delete INDEX

---
### Finding Tasks
Finds tasks containing a keyword.
Format:
find KEYWORD
Example:
find book

---
### Exiting the Application
Format:
bye

---
## Saving Data
Tasks are saved automatically and loaded when the application starts.
