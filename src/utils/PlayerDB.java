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
        PlayerDB.user = "root";
        PlayerDB.pass = "1234";
        PlayerDB.dbname = "mp3db";
    }

    public static PlayerDB getDb() {
        if (db == null) {
            db = new PlayerDB();
        }

        return db;
    }

    public Connection connect()    {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            if (conn == null) {
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbname, user, pass);
            }
        } catch(SQLException e) {
            System.out.println("The mp3 player was unable to connect to the database.");
            System.out.println(e.getMessage());
        } catch(ClassNotFoundException e) {
            System.out.println("Class not found");
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
