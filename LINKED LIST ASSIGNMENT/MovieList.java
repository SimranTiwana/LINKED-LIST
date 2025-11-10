import java.util.Scanner;

class Movie {
    String title;
    String director;
    int year;
    double rating;
    Movie prev;
    Movie next;

    Movie(String title, String director, int year, double rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.prev = null;
        this.next = null;
    }
}

public class MovieList {
    Movie head = null;
    Movie tail = null;
    Scanner sc = new Scanner(System.in);

    // Add at beginning
    void addAtBeginning() {
        System.out.print("Title: ");
        String t = sc.nextLine();
        System.out.print("Director: ");
        String d = sc.nextLine();
        System.out.print("Year: ");
        int y = Integer.parseInt(sc.nextLine());
        System.out.print("Rating: ");
        double r = Double.parseDouble(sc.nextLine());

        Movie m = new Movie(t, d, y, r);
        if (head == null) {
            head = tail = m;
        } else {
            m.next = head;
            head.prev = m;
            head = m;
        }
        System.out.println("Movie added at beginning.");
    }

    // Add at end
    void addAtEnd() {
        System.out.print("Title: ");
        String t = sc.nextLine();
        System.out.print("Director: ");
        String d = sc.nextLine();
        System.out.print("Year: ");
        int y = Integer.parseInt(sc.nextLine());
        System.out.print("Rating: ");
        double r = Double.parseDouble(sc.nextLine());

        Movie m = new Movie(t, d, y, r);
        if (tail == null) {
            head = tail = m;
        } else {
            tail.next = m;
            m.prev = tail;
            tail = m;
        }
        System.out.println("Movie added at end.");
    }

    // Add at specific position (1-based)
    void addAtPosition() {
        System.out.print("Position (1 = beginning): ");
        int pos = Integer.parseInt(sc.nextLine());
        if (pos <= 1) {
            addAtBeginning();
            return;
        }

        System.out.print("Title: ");
        String t = sc.nextLine();
        System.out.print("Director: ");
        String d = sc.nextLine();
        System.out.print("Year: ");
        int y = Integer.parseInt(sc.nextLine());
        System.out.print("Rating: ");
        double r = Double.parseDouble(sc.nextLine());

        Movie m = new Movie(t, d, y, r);

        Movie cur = head;
        int idx = 1;
        while (cur != null && idx < pos - 1) {
            cur = cur.next;
            idx++;
        }

        if (cur == null || cur == tail) { // insert at end if position past length
            addAtEnd();
        } else {
            m.next = cur.next;
            m.prev = cur;
            cur.next.prev = m;
            cur.next = m;
            System.out.println("Movie added at position " + pos + ".");
        }
    }

    // Remove by title (first match)
    void removeByTitle() {
        System.out.print("Enter Title to remove: ");
        String t = sc.nextLine();
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Movie cur = head;
        while (cur != null && !cur.title.equals(t)) {
            cur = cur.next;
        }

        if (cur == null) {
            System.out.println("Movie not found.");
            return;
        }

        if (cur == head && cur == tail) { // only one node
            head = tail = null;
        } else if (cur == head) {
            head = head.next;
            head.prev = null;
        } else if (cur == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
        }
        System.out.println("Movie removed: " + t);
    }

    // Search by director or by rating
    void searchMenu() {
        System.out.println("Search by: 1.Director  2.Rating");
        System.out.print("Choice: ");
        int ch = Integer.parseInt(sc.nextLine());
        if (ch == 1) searchByDirector();
        else if (ch == 2) searchByRating();
        else System.out.println("Invalid choice.");
    }

    void searchByDirector() {
        System.out.print("Enter Director name: ");
        String d = sc.nextLine();
        Movie cur = head;
        boolean found = false;
        while (cur != null) {
            if (cur.director.equals(d)) {
                System.out.println(cur.title + " | " + cur.director + " | " + cur.year + " | " + cur.rating);
                found = true;
            }
            cur = cur.next;
        }
        if (!found) System.out.println("No movies found by that director.");
    }

    void searchByRating() {
        System.out.print("Enter rating to search (exact match, e.g., 7.5): ");
        double r = Double.parseDouble(sc.nextLine());
        Movie cur = head;
        boolean found = false;
        while (cur != null) {
            if (Double.compare(cur.rating, r) == 0) {
                System.out.println(cur.title + " | " + cur.director + " | " + cur.year + " | " + cur.rating);
                found = true;
            }
            cur = cur.next;
        }
        if (!found) System.out.println("No movies found with that rating.");
    }

    // Display forward
    void displayForward() {
        if (head == null) {
            System.out.println("No records.");
            return;
        }
        System.out.println("---- Movies (Forward) ----");
        Movie cur = head;
        while (cur != null) {
            System.out.println(cur.title + " | " + cur.director + " | " + cur.year + " | " + cur.rating);
            cur = cur.next;
        }
    }

    // Display reverse (from tail)
    void displayReverse() {
        if (tail == null) {
            System.out.println("No records.");
            return;
        }
        System.out.println("---- Movies (Reverse) ----");
        Movie cur = tail;
        while (cur != null) {
            System.out.println(cur.title + " | " + cur.director + " | " + cur.year + " | " + cur.rating);
            cur = cur.prev;
        }
    }

    // Update rating by title (first match)
    void updateRating() {
        System.out.print("Enter Title to update rating: ");
        String t = sc.nextLine();
        Movie cur = head;
        while (cur != null && !cur.title.equals(t)) cur = cur.next;
        if (cur == null) {
            System.out.println("Movie not found.");
            return;
        }
        System.out.print("Enter new rating: ");
        double r = Double.parseDouble(sc.nextLine());
        cur.rating = r;
        System.out.println("Rating updated for " + t);
    }

    // Menu
    public static void main(String[] args) {
        MovieList ml = new MovieList();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add Beginning  2.Add End  3.Add Position  4.Remove by Title");
            System.out.println("5.Search  6.Update Rating  7.Display Forward  8.Display Reverse  9.Exit");
            System.out.print("Choice: ");
            int ch;
            try {
                ch = Integer.parseInt(s.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (ch) {
                case 1: ml.addAtBeginning(); break;
                case 2: ml.addAtEnd(); break;
                case 3: ml.addAtPosition(); break;
                case 4: ml.removeByTitle(); break;
                case 5: ml.searchMenu(); break;
                case 6: ml.updateRating(); break;
                case 7: ml.displayForward(); break;
                case 8: ml.displayReverse(); break;
                case 9: System.out.println("Goodbye!"); s.close(); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
}
