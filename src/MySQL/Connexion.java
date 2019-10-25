package MySQL;
import java.sql.*;

public class Connexion {
	
	public  static Connection creeConnexion() {
		String url = "jdbc:mysql://localhost:3306/revuesonline";
		String login = "FLedeux";
		String pwd = "Ledeux";
		Connection maConnexion = null;
		try { 
			maConnexion = (Connection) DriverManager.getConnection(url, login, pwd);
			} 
		catch (SQLException sqle) 
		{
			System.out.println("Erreur connexion " + sqle.getMessage());
			}
		return maConnexion;
	}




}