import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class DataAccess { 
    private Connection connection; 
     
    public DataAccess() throws SQLException { 
        this.connection = DriverManager.getConnection("jdbc:sqlite:attendance.db"); 
        createTables(); 
    } 
     
    private void createTables() throws SQLException { 
        String sqlStudents = "CREATE TABLE IF NOT EXISTS students (" + 
                            "id INTEGER PRIMARY KEY, " + 
                            "name TEXT NOT NULL)"; 
         
        String sqlAttendance = "CREATE TABLE IF NOT EXISTS attendance (" + 
                              "id INTEGER PRIMARY KEY, " + 
                              "student_id INTEGER, " + 
                              "date TEXT, " + 
                              "status TEXT, " + 
                              "FOREIGN KEY (student_id) REFERENCES students(id))"; 
         
        try (Statement stmt = connection.createStatement()) { 
            stmt.execute(sqlStudents); 
            stmt.execute(sqlAttendance); 
        } 
    } 
     
    public int addStudent(String name) throws SQLException { 
        String sql = "INSERT INTO students(name) VALUES(?)"; 
        try (PreparedStatement pstmt = connection.prepareStatement(sql, 
Statement.RETURN_GENERATED_KEYS)) { 
            pstmt.setString(1, name); 
            pstmt.executeUpdate(); 
             
            ResultSet rs = pstmt.getGeneratedKeys(); 
            if (rs.next()) { 
                return rs.getInt(1); 
            } 
        } 
        return -1; 
    } 
     
    public List<String> getStudents() throws SQLException { 
        List<String> students = new ArrayList<>(); 
        String sql = "SELECT id, name FROM students"; 
         
        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                students.add(rs.getInt("id") + ": " + rs.getString("name")); 
            } 
        } 
        return students; 
    } 
     
    public void markAttendance(int studentId, String date, String status) throws SQLException { 
        String sql = "INSERT INTO attendance(student_id, date, status) VALUES(?, ?, ?)"; 
         
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) { 
            pstmt.setInt(1, studentId); 
            pstmt.setString(2, date); 
            pstmt.setString(3, status); 
            pstmt.executeUpdate(); 
        } 
    } 
     
    public List<String> getAttendanceReport() throws SQLException { 
        List<String> report = new ArrayList<>(); 
        String sql = "SELECT s.name, a.date, a.status " + 
                     "FROM attendance a " + 
                     "JOIN students s ON a.student_id = s.id " + 
                     "ORDER BY a.date DESC"; 
         
        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                report.add(rs.getString("date") + ": " +  
                           rs.getString("name") + " - " +  
                           rs.getString("status")); 
            } 
        } 
        return report; 
    } 
     
    public void close() throws SQLException { 
        if (connection != null) { 
            connection.close(); 
        } 
    } 
}
