
import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            while (true) {
                System.out.println("--- Student Result Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Top Performer");
                System.out.println("6. Exit");
                System.out.print("Choose option: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter marks: ");
                        int marks = sc.nextInt();
                        stmt.executeUpdate("INSERT INTO students(name, marks) VALUES ('" + name + "', " + marks + ")");
                        System.out.println("Student added.\n");
                        break;
                    case 2:
                        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Marks: " + rs.getInt("marks"));
                        }
                        System.out.println();
                        break;
                    case 3:
                        System.out.print("Enter student ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new marks: ");
                        int newMarks = sc.nextInt();
                        stmt.executeUpdate("UPDATE students SET name='" + newName + "', marks=" + newMarks + " WHERE id=" + updateId);
                        System.out.println("Student updated.\n");
                        break;
                    case 4:
                        System.out.print("Enter student ID to delete: ");
                        int deleteId = sc.nextInt();
                        stmt.executeUpdate("DELETE FROM students WHERE id=" + deleteId);
                        System.out.println("Student deleted.\n");
                        break;
                    case 5:
                        rs = stmt.executeQuery("SELECT * FROM students ORDER BY marks DESC LIMIT 1");
                        if (rs.next()) {
                            System.out.println("Top Performer: " + rs.getString("name") + " with " + rs.getInt("marks") + " marks\n");
                        }
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        conn.close();
                        return;
                    default:
                        System.out.println("Invalid choice.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
