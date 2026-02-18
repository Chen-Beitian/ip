# Momo User Guide

Momo is a desktop task manager for users who prefer typing commands.
It allows you to manage todos, deadlines, and events efficiently through a command-based interface.

---

## Quick Start

1. Ensure you have Java 17 or above installed.
2. Download the latest `momo.jar`.
3. Open a terminal and navigate to the folder containing the jar file.
4. Run:

```
java -jar momo.jar
```

5. The GUI should appear as shown below:

![UI](Ui.png)

6. Type commands into the input box and press Enter / "Send" button to execute them.

---

## Features

### Adding a Todo

Adds a task without date or time.

Format:
```
todo DESCRIPTION
```

Example:
```
todo read book
```

---

### Adding a Deadline

Adds a task with a due date.  
The deadline can be specified with date only, or with date and time.

Format (date only):
```
deadline DESCRIPTION /by yyyy-mm-dd
```

Format (date and time):
```
deadline DESCRIPTION /by yyyy-mm-dd HH:mm
```

Date format must follow ISO format (e.g., 2026-02-15). 
Time format uses 24-hour format (e.g., 14:00).

Examples:
```
deadline return book /by 2026-02-15
deadline submit report /by 2026-02-15 23:59
```

---

### Adding an Event

Adds a task with a start and end date/time.

Format:
```
event DESCRIPTION /from yyyy-mm-dd HH:mm /to yyyy-mm-dd HH:mm
```

Time format uses 24-hour format (e.g., 14:00).

Example:
```
event project meeting /from 2026-02-15 14:00 /to 2026-02-15 16:00
```

---

### Listing Tasks

Displays all tasks.

Format:
```
list
```

---

### Marking a Task

Marks a task as done.

Format:
```
mark INDEX
```

---

### Unmarking a Task

Marks a task as not done.

Format:
```
unmark INDEX
```

---

### Deleting a Task

Deletes a task.

Format:
```
delete INDEX
```

---

### Finding Tasks

Finds tasks containing a keyword.

Format:
```
find KEYWORD
```

Example:
```
find book
```

---

### Tagging a Task

Adds a tag to an existing task.

Format:
```
tag INDEX #TAG_NAME
```

Example:
```
tag 2 #urgent
```

---

### Removing a Tag

Removes a tag from an existing task.

Format:
```
untag INDEX #TAG_NAME
```

Example:
```
untag 2 #urgent
```

---

### Filtering by Tag

Displays tasks that contain the specified tag.

Format:
```
filter #TAG_NAME
```

Example:
```
filter #urgent
```

### Exiting the Application

Format:
```
bye
```

---

## Saving Data

Tasks are saved automatically to a local file and loaded when the application starts.

---

## Acknowledgements
AI Assistance:
ChatGPT was used for
- Code review and refactoring suggestions
- Brainstorming test cases
- API usage clarification
- Documentation improvements
- Generate the avatar images used in the UI
All code was manually reviewed and adapted before integration.
