package utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class PlayerDB {
    private static String user;
    private static String pass;
    private static String dbname;
    private static Connection conn;

    private static PlayerDB db;

    private PlayerDB()  {
        user = "root";
        pass = "root";
    }

    public static PlayerDB getDb() {
        if (db == null) {
            db = new PlayerDB();
        }

        return db;
    }

    public Connection connect()    {
        try {
            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/" + dbname, user, pass);
            }
        } catch(SQLException e) {
            System.out.println("The mp3 player was unable to connect to the database.");
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void close()  {
        try {
            conn.close();
            conn = null;
        } catch(SQLException e) {
            System.out.println("The mp3 player was unable to disconnect from the database.");
            System.out.println(e.getMessage());
        }
    }
}
