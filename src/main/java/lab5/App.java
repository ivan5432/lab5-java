package lab5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    private final String url = "jdbc:postgresql://localhost:5432/db";
    private final String user = "postgres";
    private final String password = "postgres";

    private static final String patientSQL = "CREATE TABLE patients" +
            "(ID INT PRIMARY KEY ," +
            " FirstName VARCHAR(50), " +
            " MiddleName VARCHAR(50), " +
            " LastName VARCHAR(50), " +
            " ADDRESS VARCHAR(50), " +
            " AGE INT, " +
            " BloodGroup VARCHAR(50), " +
            " CONSTRAINT h FOREIGN KEY (ID) REFERENCES hospitals(ID), " +
            " SocialGroup VARCHAR(50))";

    private static final String hospitalsSQL = "CREATE TABLE hospitals" +
            "(ID INT PRIMARY KEY ," +
            " NAME VARCHAR(50)) " ;

    public void createTable(String sql_command) throws SQLException {

        System.out.println(sql_command);
        try (Connection connection = DriverManager.getConnection(url, user, password);

             Statement statement = connection.createStatement();) {

            statement.execute(sql_command);
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        App app = new App();
        app.createTable(hospitalsSQL);
        app.createTable(patientSQL);
    }
}
