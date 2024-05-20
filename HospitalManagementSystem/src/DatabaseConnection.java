import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static String url = "jdbc:mysql://localhost:3306/hospital_db";
    private static String username = "root";
    private static String password = "546982tyr78";


    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver could NOT FOUND.");
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
