class ProcessNode {
    int pid;
    int burst;
    int priority;
    int remaining;
    int originalBurst;
    int completionTime;
    ProcessNode next;

    ProcessNode(int pid, int burst, int priority) {
        this.pid = pid;
        this.burst = burst;
        this.priority = priority;
        this.remaining = burst;
        this.originalBurst = burst;
    }

    public String toString() {
        return "[" + pid + " | Rem:" + remaining + " | B:" + originalBurst + " | Pr:" + priority + "]";
    }
}

public class RoundRobinScheduler {
    private ProcessNode head = null;
    private ProcessNode current = null;
    private int totalProcesses = 0;

    public void addProcess(int pid, int burst, int priority) {
        ProcessNode node = new ProcessNode(pid, burst, priority);
        if (head == null) {
            head = node;
            node.next = node;
        } else {
            ProcessNode last = head;
            while (last.next != head) last = last.next;
            last.next = node;
            node.next = head;
        }
        if (current == null) current = head;
        totalProcesses++;
    }

    private int countNodes() {
        if (head == null) return 0;
        int c = 0;
        ProcessNode t = head;
        do {
            c++;
            t = t.next;
        } while (t != head);
        return c;
    }

    private void removeNode(ProcessNode nodeToRemove) {
        if (head == null || nodeToRemove == null) return;
        if (head == nodeToRemove && head.next == head) {
            head = null;
            current = null;
            return;
        }
        ProcessNode t = head;
        ProcessNode prev = null;
        do {
            if (t == nodeToRemove) break;
            prev = t;
            t = t.next;
        } while (t != head);
        if (t != nodeToRemove) return;
        if (t == head) {
            ProcessNode last = head;
            while (last.next != head) last = last.next;
            head = head.next;
            last.next = head;
            if (current == nodeToRemove) current = head;
        } else {
            prev.next = t.next;
            if (current == nodeToRemove) current = t.next;
        }
    }

    public void displayList() {
        if (head == null) {
            System.out.println("Queue empty");
            return;
        }
        ProcessNode t = head;
        do {
            System.out.print(t + " ");
            t = t.next;
        } while (t != head);
        System.out.println();
    }

    public void simulate(int timeQuantum) {
        if (head == null) {
            System.out.println("No processes to schedule");
            return;
        }
        int time = 0;
        int round = 1;
        double totalWaiting = 0;
        double totalTurnaround = 0;
        int finishedCount = 0;
        while (head != null) {
            int sizeAtRoundStart = countNodes();
            System.out.println("Round " + round + " start (time=" + time + ") :");
            displayList();
            for (int i = 0; i < sizeAtRoundStart && head != null; i++) {
                ProcessNode p = current;
                if (p == null) break;
                int exec = Math.min(timeQuantum, p.remaining);
                time += exec;
                p.remaining -= exec;
                if (p.remaining == 0) {
                    p.completionTime = time;
                    int turnaround = p.completionTime;
                    int waiting = turnaround - p.originalBurst;
                    totalTurnaround += turnaround;
                    totalWaiting += waiting;
                    finishedCount++;
                    ProcessNode nextNode = p.next;
                    removeNode(p);
                    current = (head == null) ? null : nextNode;
                } else {
                    current = current.next;
                }
            }
            System.out.println("Round " + round + " end (time=" + time + ") :");
            displayList();
            System.out.println();
            round++;
        }
        if (finishedCount > 0) {
            System.out.printf("Average Waiting Time = %.2f\n", totalWaiting / finishedCount);
            System.out.printf("Average Turnaround Time = %.2f\n", totalTurnaround / finishedCount);
        }
    }

    public static void main(String[] args) {
        RoundRobinScheduler rr = new RoundRobinScheduler();
        rr.addProcess(1, 10, 2);
        rr.addProcess(2, 4, 1);
        rr.addProcess(3, 6, 3);
        rr.addProcess(4, 8, 2);

        System.out.println("Initial circular queue:");
        rr.displayList();

        int quantum = 3;
        System.out.println("\nSimulating Round-Robin with quantum = " + quantum + "\n");
        rr.simulate(quantum);
    }
}
