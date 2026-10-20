import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    // Step 6: Java Program -> Main Method
    public static void main(String[] args) {
        
        String url = requireEnv("DB_URL");
        String user = requireEnv("DB_USER");
        String password = requireEnv("DB_PASSWORD");

        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Step 7: Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 8: Make Connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully.");

            // --- INSERTING DATA ---
            
            // Step 9: Create PreparedStatement
            String insertQuery = "INSERT INTO Student (id, name, branch) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);

            pstmt.setInt(1, 101);                         // id
            pstmt.setString(2, "Sahil Sepat");               // name
            pstmt.setString(3, "Computer Science");       // branch

            // Step 11: Execute
            int rowsAffected = pstmt.executeUpdate();

            // Step 12: Print
            System.out.println(rowsAffected + " row(s) inserted.");

            // --- READING DATA ---

            // Step 13: Create Statement
            stmt = conn.createStatement();

            // Step 14: Execute Query (Fixed table and column names)
            String selectQuery = "SELECT id, name, branch FROM Student";
            rs = stmt.executeQuery(selectQuery);

            // Step 15: Read ResultSet
            while (rs.next()) {
                // Step 16: Get Values (Fixed to match actual table columns)
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String branch = rs.getString("branch");

                // Step 17: Output
                System.out.println("ID: " + id + ", Name: " + name + ", Branch: " + branch);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            // Step 18: Close Connection (and other resources)
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required environment variable is not set: " + name);
        }
        return value;
    }
}