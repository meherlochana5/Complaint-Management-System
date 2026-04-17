import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/complaint_db",
                "root",
                "Mehera@1234"
            );

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }

        return con;
    }
}