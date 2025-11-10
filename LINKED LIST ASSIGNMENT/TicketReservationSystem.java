import java.util.Scanner;

class TicketNode {
    int ticketId;
    String customerName;
    String movieName;
    String seatNumber;
    String bookingTime;
    TicketNode next;

    TicketNode(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
    }

    public String toString() {
        return "[" + ticketId + " | " + customerName + " | " + movieName +
               " | Seat: " + seatNumber + " | " + bookingTime + "]";
    }
}

public class TicketReservationSystem {
    private TicketNode head = null;

    public void addAtEnd(TicketNode node) {
        if (head == null) {
            head = node;
            node.next = node;
            return;
        }
        TicketNode last = head;
        while (last.next != head) last = last.next;
        last.next = node;
        node.next = head;
    }

    public boolean removeById(int id) {
        if (head == null) return false;
        TicketNode curr = head;
        TicketNode prev = null;
        do {
            if (curr.ticketId == id) break;
            prev = curr;
            curr = curr.next;
        } while (curr != head);

        if (curr.ticketId != id) return false;

        if (curr == head && curr.next == head) {
            head = null;
            return true;
        }

        if (curr == head) {
            TicketNode last = head;
            while (last.next != head) last = last.next;
            head = head.next;
            last.next = head;
            return true;
        } else {
            prev.next = curr.next;
            return true;
        }
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No booked tickets.");
            return;
        }
        TicketNode t = head;
        do {
            System.out.println(t);
            t = t.next;
        } while (t != head);
    }

    public void searchByCustomer(String name) {
        if (head == null) {
            System.out.println("No booked tickets.");
            return;
        }
        boolean found = false;
        TicketNode t = head;
        do {
            if (t.customerName.equalsIgnoreCase(name)) {
                System.out.println(t);
                found = true;
            }
            t = t.next;
        } while (t != head);
        if (!found) System.out.println("No tickets found for: " + name);
    }

    public void searchByMovie(String movie) {
        if (head == null) {
            System.out.println("No booked tickets.");
            return;
        }
        boolean found = false;
        TicketNode t = head;
        do {
            if (t.movieName.equalsIgnoreCase(movie)) {
                System.out.println(t);
                found = true;
            }
            t = t.next;
        } while (t != head);
        if (!found) System.out.println("No tickets found for: " + movie);
    }

    public int countTickets() {
        if (head == null) return 0;
        int c = 0;
        TicketNode t = head;
        do {
            c++;
            t = t.next;
        } while (t != head);
        return c;
    }

    private static void printMenu() {
        System.out.println("\n--- Ticket Reservation Menu ---");
        System.out.println("1. Add new ticket");
        System.out.println("2. Remove ticket by ID");
        System.out.println("3. Display all tickets");
        System.out.println("4. Search by customer name");
        System.out.println("5. Search by movie name");
        System.out.println("6. Count total booked tickets");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    public static void main(String[] args) {
        TicketReservationSystem trs = new TicketReservationSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Exiting.");
                break;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Ticket ID: ");
                        int id = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Customer Name: ");
                        String cname = sc.nextLine().trim();

                        System.out.print("Movie Name: ");
                        String mname = sc.nextLine().trim();

                        System.out.print("Seat Number: ");
                        String seat = sc.nextLine().trim();

                        String time = "Time Not Stored";

                        TicketNode node = new TicketNode(id, cname, mname, seat, time);
                        trs.addAtEnd(node);
                        System.out.println("Ticket added: " + node);
                    } catch (Exception ex) {
                        System.out.println("Invalid input.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Ticket ID to remove: ");
                    int rid = Integer.parseInt(sc.nextLine().trim());
                    System.out.println(trs.removeById(rid) ? "Ticket removed." : "Ticket ID not found.");
                    break;

                case 3:
                    trs.displayAll();
                    break;

                case 4:
                    System.out.print("Enter customer name: ");
                    trs.searchByCustomer(sc.nextLine().trim());
                    break;

                case 5:
                    System.out.print("Enter movie name: ");
                    trs.searchByMovie(sc.nextLine().trim());
                    break;

                case 6:
                    System.out.println("Total booked tickets: " + trs.countTickets());
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }
}
