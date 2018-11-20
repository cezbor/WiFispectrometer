package cezbor.WiFispectrometer.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBC 
{
	private Connection connect() 
	{
        // SQLite connection string
        String url = "jdbc:sqlite:sunxi.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public String getLatest()
    {
        String sql = "SELECT file FROM CdrFile ORDER BY time DESC LIMIT 1";
        String pathStr = null;
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
             rs.next();
             pathStr = rs.getString("file");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pathStr;
    }
 
}