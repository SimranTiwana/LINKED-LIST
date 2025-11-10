import java.util.Scanner;

class Student {
    int roll;
    String name;
    int age;
    String grade;
    Student next;

    Student(int roll, String name, int age, String grade) {
        this.roll = roll;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.next = null;
    }
}

public class StudentList {
    Student head = null;
    Scanner sc = new Scanner(System.in);

    // ➤ Add at Beginning
    void addAtBeginning() {
        System.out.print("Enter Roll: ");
        int roll = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        Student newStudent = new Student(roll, name, age, grade);
        newStudent.next = head;
        head = newStudent;

        System.out.println("Inserted at Beginning!");
    }

    // ➤ Add at End
    void addAtEnd() {
        System.out.print("Enter Roll: ");
        int roll = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        Student newStudent = new Student(roll, name, age, grade);

        if (head == null) {
            head = newStudent;
        } else {
            Student temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newStudent;
        }
        System.out.println("Inserted at End!");
    }

    // ➤ Add at any Position
    void addAtPosition() {
        System.out.print("Enter Position: ");
        int pos = sc.nextInt();

        if (pos == 1) {
            addAtBeginning();
            return;
        }

        System.out.print("Enter Roll: ");
        int roll = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        Student newStudent = new Student(roll, name, age, grade);

        Student temp = head;
        for (int i = 1; i < pos - 1 && temp != null; i++) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Invalid Position!");
            return;
        }

        newStudent.next = temp.next;
        temp.next = newStudent;

        System.out.println("Inserted at Position " + pos + "!");
    }

    // ➤ Delete by Roll Number
    void deleteByRoll() {
        System.out.print("Enter Roll to Delete: ");
        int roll = sc.nextInt();

        if (head == null) {
            System.out.println("List is Empty!");
            return;
        }

        if (head.roll == roll) {
            head = head.next;
            System.out.println("Deleted Successfully!");
            return;
        }

        Student temp = head;
        while (temp.next != null && temp.next.roll != roll)
            temp = temp.next;

        if (temp.next == null)
            System.out.println("Record Not Found!");
        else {
            temp.next = temp.next.next;
            System.out.println("Deleted Successfully!");
        }
    }

    // ➤ Search by Roll Number
    void searchByRoll() {
        System.out.print("Enter Roll to Search: ");
        int roll = sc.nextInt();

        Student temp = head;
        while (temp != null) {
            if (temp.roll == roll) {
                System.out.println("Found: " + temp.name + ", Age: " + temp.age + ", Grade: " + temp.grade);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Record Not Found!");
    }

    // ➤ Update Grade by Roll Number
    void updateGrade() {
        System.out.print("Enter Roll to Update: ");
        int roll = sc.nextInt();
        sc.nextLine();

        Student temp = head;
        while (temp != null) {
            if (temp.roll == roll) {
                System.out.print("Enter New Grade: ");
                temp.grade = sc.nextLine();
                System.out.println("Grade Updated!");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Record Not Found!");
    }

    // ➤ Display All Students
    void display() {
        if (head == null) {
            System.out.println("No Records Available!");
            return;
        }
        Student temp = head;
        System.out.println("----- Student Records -----");
        while (temp != null) {
            System.out.println("Roll: " + temp.roll + ", Name: " + temp.name + ", Age: " + temp.age + ", Grade: " + temp.grade);
            temp = temp.next;
        }
    }

    // ➤ Main Menu
    public static void main(String[] args) {
        StudentList list = new StudentList();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add at Beginning\n2.Add at End\n3.Add at Position\n4.Delete by Roll\n5.Search\n6.Update Grade\n7.Display\n8.Exit");
            System.out.print("Enter Choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: list.addAtBeginning(); break;
                case 2: list.addAtEnd(); break;
                case 3: list.addAtPosition(); break;
                case 4: list.deleteByRoll(); break;
                case 5: list.searchByRoll(); break;
                case 6: list.updateGrade(); break;
                case 7: list.display(); break;
                case 8: System.out.println("Thank You!"); return;
                default: System.out.println("Invalid Choice!");
            }
        }
    }
}
