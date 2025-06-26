## Attendance Management System (3-Tier Java Application)
A complete Java implementation of an attendance tracking system using SQLite database with 3-tier architecture.
## 📂 Project Structure
\attendance-system\
├── src\
│   ├── AttendanceService.java    # Business Logic Layer
│   ├── AttendanceUI.java        # Presentation Layer
│   └── DataAccess.java          # Data Access Layer
├── out\
│   ├── AttendanceService.class
│   ├── AttendanceUI.class
│   └── DataAccess.class
├── lib\
│   └── sqlite-jdbc-3.50.1.0.jar  # SQLite JDBC Driver
└── attendance.db (generates automatically when compiled)                # SQLite Database
## 🛠️ Prerequisites
-Java JDK 8+
-SQLite JDBC driver (included in lib/)
-Basic command line knowledge
## 🚀 How to Compile and Run
## Compile DataAccess
Windows:
javac -cp .;sqlite-jdbc.jar DataAccess.java
## Compile AttendanceService
javac -cp .;sqlite-jdbc.jar AttendanceService.java
## Compile AttendanceUI
javac -cp .;sqlite-jdbc.jar AttendanceUI.java
## Compile sqlite
javac -cp .;sqlite-jdbc.jar *.java
## run the application
java -cp .;sqlite-jdbc.jar AttendanceUI
or
java -cp ".;sqlite-jdbc-3.50.1.0.jar" AttendanceUI
