class BookNode {
    String title;
    String author;
    String genre;
    int id;
    boolean available;
    BookNode prev;
    BookNode next;

    BookNode(String title, String author, String genre, int id, boolean available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.id = id;
        this.available = available;
    }

    public String toString() {
        return "[" + id + " | " + title + " | " + author + " | " + genre + " | " + (available ? "Available" : "Checked Out") + "]";
    }
}

public class LibraryManagement {
    private BookNode head = null;

    public void addAtBeginning(BookNode node) {
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    public void addAtEnd(BookNode node) {
        if (head == null) {
            head = node;
            return;
        }
        BookNode temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = node;
        node.prev = temp;
    }

    public void addAtPosition(BookNode node, int pos) {
        if (head == null || pos <= 1) {
            addAtBeginning(node);
            return;
        }
        BookNode temp = head;
        int idx = 1;
        while (temp.next != null && idx < pos - 1) {
            temp = temp.next;
            idx++;
        }
        node.next = temp.next;
        if (temp.next != null) temp.next.prev = node;
        temp.next = node;
        node.prev = temp;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        BookNode temp = head;
        while (temp != null && temp.id != id) temp = temp.next;
        if (temp == null) return false;
        if (temp == head) {
            head = head.next;
            if (head != null) head.prev = null;
            return true;
        }
        if (temp.next != null) temp.next.prev = temp.prev;
        if (temp.prev != null) temp.prev.next = temp.next;
        return true;
    }

    public BookNode searchByTitle(String title) {
        BookNode temp = head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) return temp;
            temp = temp.next;
        }
        return null;
    }

    public BookNode searchByAuthor(String author) {
        BookNode temp = head;
        while (temp != null) {
            if (temp.author.equalsIgnoreCase(author)) return temp;
            temp = temp.next;
        }
        return null;
    }

    public boolean updateAvailability(int id, boolean available) {
        BookNode temp = head;
        while (temp != null) {
            if (temp.id == id) {
                temp.available = available;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void displayForward() {
        if (head == null) {
            System.out.println("Library is empty");
            return;
        }
        BookNode temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public void displayReverse() {
        if (head == null) {
            System.out.println("Library is empty");
            return;
        }
        BookNode temp = head;
        while (temp.next != null) temp = temp.next;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.prev;
        }
    }

    public int countBooks() {
        int count = 0;
        BookNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public static void main(String[] args) {
        LibraryManagement lib = new LibraryManagement();

        lib.addAtEnd(new BookNode("The Alchemist", "Paulo Coelho", "Fiction", 201, true));
        lib.addAtEnd(new BookNode("Clean Code", "Robert C. Martin", "Programming", 202, true));
        lib.addAtBeginning(new BookNode("1984", "George Orwell", "Dystopia", 200, false));
        lib.addAtPosition(new BookNode("Design Patterns", "Erich Gamma", "Programming", 203, true), 3);

        System.out.println("All books (forward):");
        lib.displayForward();

        System.out.println("\nAll books (reverse):");
        lib.displayReverse();

        System.out.println("\nTotal books: " + lib.countBooks());

        System.out.println("\nSearch by title 'Clean Code': " + lib.searchByTitle("Clean Code"));
        System.out.println("Search by author 'George Orwell': " + lib.searchByAuthor("George Orwell"));

        System.out.println("\nUpdate availability of ID 200 to true: " + lib.updateAvailability(200, true));
        lib.displayForward();

        System.out.println("\nRemove book with ID 202: " + lib.removeById(202));
        lib.displayForward();
        System.out.println("\nTotal books after removal: " + lib.countBooks());
    }
}
