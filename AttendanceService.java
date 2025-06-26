import java.sql.SQLException; 
import java.util.List; 
 
public class AttendanceService { 
    private DataAccess dataAccess; 
     
    public AttendanceService(DataAccess dataAccess) { 
        this.dataAccess = dataAccess; 
    } 
     
    public int registerStudent(String name) throws SQLException { 
        return dataAccess.addStudent(name); 
    } 
     
    public List<String> getStudentList() throws SQLException { 
        return dataAccess.getStudents(); 
    } 
     
    public void takeAttendance(int studentId, String date, String status) throws SQLException { 
        dataAccess.markAttendance(studentId, date, status); 
    } 
     
    public List<String> generateReport() throws SQLException { 
        return dataAccess.getAttendanceReport(); 
    } 
}
