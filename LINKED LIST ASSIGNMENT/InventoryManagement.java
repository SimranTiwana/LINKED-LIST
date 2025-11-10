class ItemNode {
    String name;
    int id;
    int quantity;
    double price;
    ItemNode next;

    ItemNode(String name, int id, int quantity, double price) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String toString() {
        return "[" + id + " | " + name + " | Qty:" + quantity + " | Price:" + price + "]";
    }
}

public class InventoryManagement {
    private ItemNode head = null;

    public void addAtBeginning(ItemNode node) {
        node.next = head;
        head = node;
    }

    public void addAtEnd(ItemNode node) {
        if (head == null) {
            head = node;
            return;
        }
        ItemNode temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = node;
    }

    public void addAtPosition(ItemNode node, int pos) {
        if (head == null || pos <= 1) {
            addAtBeginning(node);
            return;
        }
        ItemNode temp = head;
        int i = 1;
        while (temp.next != null && i < pos - 1) {
            temp = temp.next;
            i++;
        }
        node.next = temp.next;
        temp.next = node;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        if (head.id == id) {
            head = head.next;
            return true;
        }
        ItemNode temp = head;
        while (temp.next != null && temp.next.id != id) temp = temp.next;
        if (temp.next == null) return false;
        temp.next = temp.next.next;
        return true;
    }

    public boolean updateQuantity(int id, int newQty) {
        ItemNode temp = head;
        while (temp != null) {
            if (temp.id == id) {
                temp.quantity = newQty;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public ItemNode searchById(int id) {
        ItemNode temp = head;
        while (temp != null) {
            if (temp.id == id) return temp;
            temp = temp.next;
        }
        return null;
    }

    public ItemNode searchByName(String name) {
        ItemNode temp = head;
        while (temp != null) {
            if (temp.name.equalsIgnoreCase(name)) return temp;
            temp = temp.next;
        }
        return null;
    }

    public double totalValue() {
        double sum = 0;
        ItemNode temp = head;
        while (temp != null) {
            sum += temp.price * temp.quantity;
            temp = temp.next;
        }
        return sum;
    }

    public void sortByName(boolean ascending) {
        head = mergeSortByName(head, ascending);
    }

    public void sortByPrice(boolean ascending) {
        head = mergeSortByPrice(head, ascending);
    }

    private ItemNode mergeSortByName(ItemNode node, boolean asc) {
        if (node == null || node.next == null) return node;
        ItemNode mid = getMid(node);
        ItemNode right = mid.next;
        mid.next = null;
        ItemNode leftSorted = mergeSortByName(node, asc);
        ItemNode rightSorted = mergeSortByName(right, asc);
        return mergeName(leftSorted, rightSorted, asc);
    }

    private ItemNode mergeName(ItemNode a, ItemNode b, boolean asc) {
        ItemNode dummy = new ItemNode("", 0, 0, 0);
        ItemNode tail = dummy;
        while (a != null && b != null) {
            if ((asc && a.name.compareToIgnoreCase(b.name) <= 0) ||
                (!asc && a.name.compareToIgnoreCase(b.name) > 0)) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    private ItemNode mergeSortByPrice(ItemNode node, boolean asc) {
        if (node == null || node.next == null) return node;
        ItemNode mid = getMid(node);
        ItemNode right = mid.next;
        mid.next = null;
        ItemNode leftSorted = mergeSortByPrice(node, asc);
        ItemNode rightSorted = mergeSortByPrice(right, asc);
        return mergePrice(leftSorted, rightSorted, asc);
    }

    private ItemNode mergePrice(ItemNode a, ItemNode b, boolean asc) {
        ItemNode dummy = new ItemNode("", 0, 0, 0);
        ItemNode tail = dummy;
        while (a != null && b != null) {
            if ((asc && a.price <= b.price) ||
                (!asc && a.price > b.price)) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    private ItemNode getMid(ItemNode node) {
        ItemNode slow = node, fast = node.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public void display() {
        if (head == null) {
            System.out.println("Empty");
            return;
        }
        ItemNode temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        InventoryManagement inv = new InventoryManagement();

        inv.addAtEnd(new ItemNode("Laptop", 101, 5, 55000));
        inv.addAtEnd(new ItemNode("Mouse", 102, 20, 500));
        inv.addAtBeginning(new ItemNode("Keyboard", 103, 10, 1000));
        inv.addAtPosition(new ItemNode("Monitor", 104, 7, 8000), 2);

        System.out.println("Inventory:");
        inv.display();

        System.out.println("\nSearch ID 102: " + inv.searchById(102));

        inv.updateQuantity(101, 8);
        System.out.println("\nAfter Quantity Update:");
        inv.display();

        System.out.println("\nTotal Inventory Value: " + inv.totalValue());

        inv.sortByName(true);
        System.out.println("\nSorted by Name (ASC):");
        inv.display();

        inv.sortByPrice(false);
        System.out.println("\nSorted by Price (DESC):");
        inv.display();

        inv.removeById(103);
        System.out.println("\nAfter Removing ID 103:");
        inv.display();
    }
}
