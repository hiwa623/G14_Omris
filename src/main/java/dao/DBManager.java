package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	//XAMPP(MYSQL)での接続用
//	private static final String URL = 
//			"jdbc:mysql://localhost:3306/sinnkyuutest";
//	private static final String USER = "root";
//    private static final String PASS = "";
	private static final String URL =
			"jdbc:oracle:thin:@//10.40.112.10:1521/dbsys.jz.jec.ac.jp";
	private static final String USER = "jz240137";
	private static final String PASS = "pass";
	
    

    static {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
        	Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();	
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}