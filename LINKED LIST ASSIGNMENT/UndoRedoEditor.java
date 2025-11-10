import java.util.Scanner;

class StateNode {
    String text;
    StateNode prev;
    StateNode next;

    StateNode(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}

public class UndoRedoEditor {
    private StateNode head = null;
    private StateNode tail = null;
    private StateNode current = null;
    private int size = 0;
    private final int MAX_HISTORY;

    public UndoRedoEditor(int maxHistory) {
        this.MAX_HISTORY = maxHistory;
        addState(""); // initial empty state
    }

    public void addState(String text) {
        if (current != tail) {
            StateNode t = current.next;
            while (t != null) {
                StateNode nxt = t.next;
                t.prev = null;
                t.next = null;
                size--;
                t = nxt;
            }
            current.next = null;
            tail = current;
        }
        StateNode node = new StateNode(text);
        if (head == null) {
            head = tail = current = node;
            size = 1;
            return;
        }
        tail.next = node;
        node.prev = tail;
        tail = node;
        current = node;
        size++;
        while (size > MAX_HISTORY) {
            StateNode old = head;
            head = head.next;
            if (head != null) head.prev = null;
            old.next = null;
            size--;
            if (current == old) current = head;
            if (head == null) {
                tail = current = null;
            }
        }
    }

    public boolean undo() {
        if (current == null || current.prev == null) return false;
        current = current.prev;
        return true;
    }

    public boolean redo() {
        if (current == null || current.next == null) return false;
        current = current.next;
        return true;
    }

    public String getCurrent() {
        return current == null ? "" : current.text;
    }

    public void showHistory() {
        StateNode t = head;
        int idx = 1;
        while (t != null) {
            String mark = (t == current) ? " <== current" : "";
            System.out.println(idx + ". " + t.text + mark);
            t = t.next;
            idx++;
        }
        System.out.println("History size: " + size + " (limit " + MAX_HISTORY + ")");
    }

    private static void printMenu() {
        System.out.println("\n--- Undo/Redo Text Editor ---");
        System.out.println("1. Type / Add state");
        System.out.println("2. Undo");
        System.out.println("3. Redo");
        System.out.println("4. Show current text");
        System.out.println("5. Show history");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UndoRedoEditor editor = new UndoRedoEditor(10);
        while (true) {
            printMenu();
            String in = sc.nextLine().trim();
            if (in.isEmpty()) continue;
            int ch;
            try {
                ch = Integer.parseInt(in);
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                continue;
            }
            if (ch == 0) break;
            switch (ch) {
                case 1:
                    System.out.print("Enter new text state: ");
                    String s = sc.nextLine();
                    editor.addState(s);
                    System.out.println("State added. Current: " + editor.getCurrent());
                    break;
                case 2:
                    if (editor.undo()) System.out.println("Undone. Current: " + editor.getCurrent());
                    else System.out.println("Cannot undo.");
                    break;
                case 3:
                    if (editor.redo()) System.out.println("Redone. Current: " + editor.getCurrent());
                    else System.out.println("Cannot redo.");
                    break;
                case 4:
                    System.out.println("Current text: " + editor.getCurrent());
                    break;
                case 5:
                    editor.showHistory();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }
}
