 
import java.sql.SQLException; 
import java.util.List; 
import java.util.Scanner; 
 
public class AttendanceUI { 
    private AttendanceService service; 
    private Scanner scanner; 
     
    public AttendanceUI(AttendanceService service) { 
        this.service = service; 
        this.scanner = new Scanner(System.in); 
    } 
     
    public void showMenu() { 
        while (true) { 
            System.out.println("\nAttendance Management System"); 
            System.out.println("1. Register Student"); 
            System.out.println("2. Take Attendance"); 
            System.out.println("3. View Report"); 
            System.out.println("4. Exit"); 
             
            System.out.print("Enter your choice: "); 
            String choice = scanner.nextLine(); 
             
            try { 
                switch (choice) { 
                    case "1": 
                        registerStudent(); 
                        break; 
                    case "2": 
                        takeAttendance(); 
                        break; 
                    case "3": 
                        viewReport(); 
                        break; 
                    case "4": 
                        return; 
                    default: 
                        System.out.println("Invalid choice. Please try again."); 
                } 
            } catch (SQLException e) { 
                System.out.println("Database error: " + e.getMessage()); 
            } 
        } 
    } 
     
    private void registerStudent() throws SQLException { 
        System.out.print("Enter student name: "); 
        String name = scanner.nextLine(); 
        int studentId = service.registerStudent(name); 
        System.out.println("Student registered with ID: " + studentId); 
    } 
     
    private void takeAttendance() throws SQLException { 
        List<String> students = service.getStudentList(); 
        System.out.println("\nStudent List:"); 
        for (String student : students) { 
            System.out.println(student); 
        } 
         
        System.out.print("Enter student ID: "); 
        int studentId = Integer.parseInt(scanner.nextLine()); 
         
        System.out.print("Enter date (YYYY-MM-DD) or press Enter for today: "); 
        String date = scanner.nextLine(); 
        if (date.isEmpty()) { 
            date = java.time.LocalDate.now().toString(); 
        } 
         
        System.out.print("Present/Absent? (P/A): "); 
        String status = scanner.nextLine().toUpperCase(); 
        while (!status.equals("P") && !status.equals("A")) { 
            System.out.println("Invalid status. Please enter P for Present or A for Absent"); 
            status = scanner.nextLine().toUpperCase(); 
        } 
         
        String statusFull = status.equals("P") ? "Present" : "Absent"; 
        service.takeAttendance(studentId, date, statusFull); 
        System.out.println("Attendance marked successfully"); 
    } 
     
    private void viewReport() throws SQLException { 
        System.out.println("\nAttendance Report:"); 
        System.out.println("-----------------"); 
        List<String> records = service.generateReport(); 
        for (String record : records) { 
            System.out.println(record); 
        } 
    } 
     
    public static void main(String[] args) { 
        try { 
            DataAccess dataAccess = new DataAccess(); 
            AttendanceService service = new AttendanceService(dataAccess); 
            AttendanceUI ui = new AttendanceUI(service); 
            ui.showMenu(); 
            dataAccess.close(); 
        } catch (SQLException e) { 
            System.out.println("Failed to initialize database: " + e.getMessage()); 
        } 
    } 
}
