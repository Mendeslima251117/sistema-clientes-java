package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	 private static final String URL = "jdbc:mysql://localhost:3306/login_java";
	    private static final String USER = "root";
	    private static final String PASS = "";

	    public static Connection conectar() throws Exception {
	        return DriverManager.getConnection(URL, USER, PASS);
	    }

}
