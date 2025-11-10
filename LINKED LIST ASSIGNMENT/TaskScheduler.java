class TaskNode {
    int id;
    String name;
    int priority;
    String dueDate;
    TaskNode next;

    TaskNode(int id, String name, int priority, String dueDate) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String toString() {
        return "[" + id + " | " + name + " | P:" + priority + " | due:" + dueDate + "]";
    }
}

public class TaskScheduler {
    private TaskNode head = null;
    private TaskNode current = null;

    public void addAtBeginning(TaskNode node) {
        if (head == null) {
            head = node;
            node.next = node;
        } else {
            TaskNode last = head;
            while (last.next != head) last = last.next;
            node.next = head;
            last.next = node;
            head = node;
        }
        if (current == null) current = head;
    }

    public void addAtEnd(TaskNode node) {
        if (head == null) {
            addAtBeginning(node);
            return;
        }
        TaskNode last = head;
        while (last.next != head) last = last.next;
        last.next = node;
        node.next = head;
    }

    public void addAtPosition(TaskNode node, int pos) {
        if (head == null || pos <= 1) {
            addAtBeginning(node);
            return;
        }
        TaskNode temp = head;
        int idx = 1;
        while (temp.next != head && idx < pos - 1) {
            temp = temp.next;
            idx++;
        }
        node.next = temp.next;
        temp.next = node;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        TaskNode prev = null, curr = head;
        do {
            if (curr.id == id) break;
            prev = curr;
            curr = curr.next;
        } while (curr != head);

        if (curr.id != id) return false;

        if (curr == head && curr.next == head) {
            head = null;
            current = null;
            return true;
        }

        if (curr == head) {
            TaskNode last = head;
            while (last.next != head) last = last.next;
            head = head.next;
            last.next = head;
            if (current == curr) current = head;
            return true;
        } else {
            prev.next = curr.next;
            if (current == curr) current = curr.next;
            return true;
        }
    }

    public TaskNode viewCurrent() {
        return current;
    }

    public TaskNode moveNext() {
        if (current == null) return null;
        current = current.next;
        return current;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No tasks.");
            return;
        }
        TaskNode temp = head;
        do {
            System.out.println(temp);
            temp = temp.next;
        } while (temp != head);
    }

    public void searchByPriority(int p) {
        if (head == null) {
            System.out.println("No tasks.");
            return;
        }
        TaskNode temp = head;
        boolean found = false;
        do {
            if (temp.priority == p) {
                System.out.println(temp);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);
        if (!found) System.out.println("No tasks with priority " + p);
    }

    public static void main(String[] args) {
        TaskScheduler ts = new TaskScheduler();
        ts.addAtEnd(new TaskNode(1, "Backup DB", 2, "2025-11-10"));
        ts.addAtEnd(new TaskNode(2, "Email report", 1, "2025-11-08"));
        ts.addAtBeginning(new TaskNode(3, "Fix bug #42", 1, "2025-11-07"));
        ts.addAtPosition(new TaskNode(4, "Deploy", 3, "2025-11-09"), 2);

        System.out.println("All tasks:");
        ts.displayAll();

        System.out.println("\nCurrent task: " + ts.viewCurrent());
        System.out.println("Move next -> " + ts.moveNext());
        System.out.println("Move next -> " + ts.moveNext());

        System.out.println("\nSearch priority 1:");
        ts.searchByPriority(1);

        System.out.println("\nRemove task id 3: " + ts.removeById(3));
        ts.displayAll();
    }
}
