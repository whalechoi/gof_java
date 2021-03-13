package cn.whalechoi.sources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cn.whalechoi.bases.Student;
import cn.whalechoi.interfaces.InterfaceSource;
import cn.whalechoi.tools.MyInput;

public class DatabaseSource implements InterfaceSource {
    private String dbPath = "jdbc:sqlite:";
    private Connection dbConnection = null;

    @Override
    public void setSource() {
        System.out.print("\033[32;1m" + "[Interaction]" + "\033[0m" + " Please input the sqlite3 db file(.db) path:");
        if (MyInput.scanner.hasNextLine()) {
            dbPath += MyInput.scanner.nextLine();
        }
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Set sqlite3 db source file path success!");
    }

    @Override
    public boolean openSource() {
        try {
            dbConnection = DriverManager.getConnection(dbPath);
            System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Open sqlite3 db source file success!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Open sqlite3 db source file failed!");
        return false;
    }

    @Override
    public ArrayList<Student> getData() {
        if (dbPath == "jdbc:sqlite:") {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Please set sqlite3 db file path first!");
            return null;
        }
        if (dbConnection == null) {
            System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Please open sqlite3 db first!");
            return null;
        }
        String sql = "SELECT id, name, sex, age FROM StudentInfo";
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Student> students = new ArrayList<Student>();
            while (rs.next()) {
                Student stu = new Student();
                stu.setId(rs.getInt("id"));
                stu.setName(rs.getString("name"));
                stu.setSex(rs.getString("sex"));
                stu.setAge(rs.getInt("age"));
                students.add(stu);
            }
            System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Get data from sqlite3 db source file success!");
            return students;
        } catch (SQLException e) {
            System.out.println(
                    "\033[31;1m" + "[Error]" + "\033[0m" + " Please make sure the source db format is correct!");
            e.printStackTrace();
        }
        System.out.println("\033[31;1m" + "[Error]" + "\033[0m" + " Get data from sqlite3 db source file failed!");
        return null;
    }

    @Override
    public boolean closeSource() {
        dbPath = "jdbc:sqlite:";
        try {
            if (dbConnection != null) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        dbConnection = null;
        System.out.println("\033[34;1m" + "[Info]" + "\033[0m" + " Close sqlite3 db source file success!");
        return true;
    }
}
