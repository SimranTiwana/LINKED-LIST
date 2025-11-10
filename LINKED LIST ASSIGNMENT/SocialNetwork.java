import java.util.Scanner;

class FriendNode {
    int friendId;
    FriendNode next;

    FriendNode(int id) {
        this.friendId = id;
    }
}

class UserNode {
    int id;
    String name;
    int age;
    FriendNode friendHead;
    UserNode next;

    UserNode(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "[" + id + " | " + name + " | Age:" + age + "]";
    }
}

public class SocialNetwork {
    private UserNode head = null;

    public void addUser(int id, String name, int age) {
        if (findById(id) != null) {
            System.out.println("User ID exists.");
            return;
        }
        UserNode n = new UserNode(id, name, age);
        if (head == null) {
            head = n;
            return;
        }
        UserNode t = head;
        while (t.next != null) t = t.next;
        t.next = n;
    }

    public UserNode findById(int id) {
        UserNode t = head;
        while (t != null) {
            if (t.id == id) return t;
            t = t.next;
        }
        return null;
    }

    public UserNode findByName(String name) {
        UserNode t = head;
        while (t != null) {
            if (t.name.equalsIgnoreCase(name)) return t;
            t = t.next;
        }
        return null;
    }

    private boolean addFriend(UserNode u, int id) {
        FriendNode f = u.friendHead;
        while (f != null) {
            if (f.friendId == id) return false;
            f = f.next;
        }
        FriendNode nn = new FriendNode(id);
        nn.next = u.friendHead;
        u.friendHead = nn;
        return true;
    }

    private boolean removeFriend(UserNode u, int id) {
        FriendNode t = u.friendHead, p = null;
        while (t != null && t.friendId != id) {
            p = t; t = t.next;
        }
        if (t == null) return false;
        if (p == null) u.friendHead = t.next;
        else p.next = t.next;
        return true;
    }

    public void addFriendConnection(int id1, int id2) {
        if (id1 == id2) {
            System.out.println("Same ID.");
            return;
        }
        UserNode u1 = findById(id1);
        UserNode u2 = findById(id2);
        if (u1 == null || u2 == null) {
            System.out.println("User missing.");
            return;
        }
        boolean a = addFriend(u1, id2);
        boolean b = addFriend(u2, id1);
        if (a || b) System.out.println("Friends added.");
        else System.out.println("Already friends.");
    }

    public void removeFriendConnection(int id1, int id2) {
        UserNode u1 = findById(id1);
        UserNode u2 = findById(id2);
        if (u1 == null || u2 == null) {
            System.out.println("User missing.");
            return;
        }
        boolean a = removeFriend(u1, id2);
        boolean b = removeFriend(u2, id1);
        if (a || b) System.out.println("Removed.");
        else System.out.println("Not friends.");
    }

    public void mutualFriends(int id1, int id2) {
        UserNode u1 = findById(id1);
        UserNode u2 = findById(id2);
        if (u1 == null || u2 == null) {
            System.out.println("User missing.");
            return;
        }
        FriendNode f1 = u1.friendHead;
        boolean found = false;
        System.out.println("Mutual friends:");
        while (f1 != null) {
            FriendNode f2 = u2.friendHead;
            while (f2 != null) {
                if (f1.friendId == f2.friendId) {
                    UserNode mf = findById(f1.friendId);
                    System.out.println(" - " + mf.id + " : " + mf.name);
                    found = true;
                }
                f2 = f2.next;
            }
            f1 = f1.next;
        }
        if (!found) System.out.println("None");
    }

    public void displayFriends(int id) {
        UserNode u = findById(id);
        if (u == null) {
            System.out.println("User missing.");
            return;
        }
        FriendNode f = u.friendHead;
        if (f == null) {
            System.out.println("No friends.");
            return;
        }
        System.out.println("Friends of " + u.name + ":");
        while (f != null) {
            UserNode uf = findById(f.friendId);
            System.out.println(" - " + uf.id + " : " + uf.name);
            f = f.next;
        }
    }

    public void countFriends() {
        UserNode t = head;
        while (t != null) {
            int c = 0;
            FriendNode f = t.friendHead;
            while (f != null) {
                c++;
                f = f.next;
            }
            System.out.println(t.name + ": " + c);
            t = t.next;
        }
    }

    public void displayUsers() {
        UserNode t = head;
        while (t != null) {
            System.out.println(t);
            t = t.next;
        }
    }

    private static void menu() {
        System.out.println("\n1 Add User");
        System.out.println("2 Add Friend");
        System.out.println("3 Remove Friend");
        System.out.println("4 Mutual Friends");
        System.out.println("5 Display Friends");
        System.out.println("6 Search User by ID");
        System.out.println("7 Search User by Name");
        System.out.println("8 Display Users");
        System.out.println("9 Count Friends");
        System.out.println("0 Exit");
        System.out.print("Choice: ");
    }

    public static void main(String[] args) {
        SocialNetwork sn = new SocialNetwork();
        Scanner sc = new Scanner(System.in);
        while (true) {
            menu();
            int ch = Integer.parseInt(sc.nextLine());
            if (ch == 0) break;
            switch (ch) {
                case 1:
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Name: ");
                    String n = sc.nextLine();
                    System.out.print("Age: ");
                    int a = Integer.parseInt(sc.nextLine());
                    sn.addUser(id, n, a);
                    break;
                case 2:
                    System.out.print("User1 ID: ");
                    int a1 = Integer.parseInt(sc.nextLine());
                    System.out.print("User2 ID: ");
                    int a2 = Integer.parseInt(sc.nextLine());
                    sn.addFriendConnection(a1, a2);
                    break;
                case 3:
                    System.out.print("User1 ID: ");
                    int r1 = Integer.parseInt(sc.nextLine());
                    System.out.print("User2 ID: ");
                    int r2 = Integer.parseInt(sc.nextLine());
                    sn.removeFriendConnection(r1, r2);
                    break;
                case 4:
                    System.out.print("User1 ID: ");
                    int m1 = Integer.parseInt(sc.nextLine());
                    System.out.print("User2 ID: ");
                    int m2 = Integer.parseInt(sc.nextLine());
                    sn.mutualFriends(m1, m2);
                    break;
                case 5:
                    System.out.print("ID: ");
                    sn.displayFriends(Integer.parseInt(sc.nextLine()));
                    break;
                case 6:
                    System.out.print("ID: ");
                    UserNode u1 = sn.findById(Integer.parseInt(sc.nextLine()));
                    System.out.println(u1 == null ? "Missing" : u1);
                    break;
                case 7:
                    System.out.print("Name: ");
                    UserNode u2 = sn.findByName(sc.nextLine());
                    System.out.println(u2 == null ? "Missing" : u2);
                    break;
                case 8:
                    sn.displayUsers();
                    break;
                case 9:
                    sn.countFriends();
                    break;
            }
        }
        sc.close();
    }
}
