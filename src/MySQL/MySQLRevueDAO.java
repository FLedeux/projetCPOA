package MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.RevueDAO;
import metier.Periodicite;
import metier.Revue;

public class MySQLRevueDAO implements RevueDAO{
	
	private static MySQLRevueDAO instance;
	
	private MySQLRevueDAO() {}

	public static MySQLRevueDAO getInstance() {
		if (instance==null) {
			instance = new MySQLRevueDAO();
		}
		return instance;
	}



	@Override
	public boolean create(Revue revue) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			
			PreparedStatement requete = laConnexion.prepareStatement("Select count(*) from Revue where titre = ? AND description = ? AND tarif_numero = ? AND id_periodicite = ?");
			requete.setString(1,revue.getTitre());
			requete.setString(2,revue.getDescription());
			requete.setDouble(3,revue.getTarif_numero());
			requete.setInt(4,revue.getPerio().getId());

			ResultSet result = requete.executeQuery();
			result.next();
			if (result.getInt(1)>0) {
				throw (new IllegalArgumentException("Cette revue existe déjà"));
			}
			
			requete = laConnexion.prepareStatement("insert into Revue (titre, description, tarif_numero, visuel, id_periodicite) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			requete.setString(1, revue.getTitre());
			requete.setString(2, revue.getDescription());
			requete.setDouble(3, revue.getTarif_numero());
			requete.setString(4, revue.getVisuel());
			requete.setInt(5, revue.getPerio().getId());
			int res = requete.executeUpdate();
			
			if (res == 1) {
				ResultSet key = requete.getGeneratedKeys();
				
				if(key.next()) revue.setId(key.getInt(1));
			}
			
			if (requete != null)requete.close();
			
			if (laConnexion != null)laConnexion.close();
			
			return res==1;
			}
			catch (SQLException sqle) {
				System.out.println("tt");
				System.out.println("Pb select" + sqle.getMessage());
			return false;	
			}
	}

	@Override
	public boolean update(Revue revue) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			
			PreparedStatement requete = laConnexion.prepareStatement("Select count(*) from Revue where titre = ? AND description = ? AND tarif_numero = ? AND id_periodicite = ?");
			requete.setString(1,revue.getTitre());
			requete.setString(2,revue.getDescription());
			requete.setDouble(3,revue.getTarif_numero());
			requete.setInt(4,revue.getPerio().getId());
			
			ResultSet result = requete.executeQuery();
			result.next();
			if (result.getInt(1)>0) {
				throw (new IllegalArgumentException("Cette revue existe déjà"));
			}
			
			requete = laConnexion.prepareStatement("update Revue set titre=?, description=?,tarif_numero=?,visuel=?,id_periodicite=?  where id_revue=?");
			requete.setString(1, revue.getTitre());
			requete.setString(2, revue.getDescription());
			requete.setDouble(3, revue.getTarif_numero());
			requete.setString(4, revue.getVisuel());
			requete.setInt(5, revue.getPerio().getId());
			requete.setInt(6, revue.getId());
			
			int res = requete.executeUpdate();
			
			if (requete != null)requete.close();
			
			if (laConnexion != null)laConnexion.close();
			
			if(res<1) {
				throw (new IllegalArgumentException("Aucun objet ne possï¿½de cet identifiant"));
			}
			
			return res==1;
			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return false;
				}
	}

	@Override
	public boolean delete(Revue revue) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			
			PreparedStatement requete = laConnexion.prepareStatement("Select count(*) from Abonnement where id_revue = ?");
			requete.setInt(1,revue.getId());
			ResultSet result = requete.executeQuery();
			result.next();
			if (result.getInt(1)>0) {
				throw (new IllegalArgumentException("Impossible de supprimer une revue utilisé autre part"));
			}
			
			requete = laConnexion.prepareStatement("delete from Revue where id_revue=?");
			requete.setInt(1, revue.getId());
			int res = requete.executeUpdate();
			
			
			if (requete != null)requete.close();
			
			if (laConnexion != null)laConnexion.close();
			
			if(res<1) {
				throw (new IllegalArgumentException("Aucun objet ne possï¿½de cet identifiant"));
			}
			
			return res==1;
			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return false;

				}
	}

	

	@Override
	public Revue getById(int id) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("DROP TABLE IF EXISTS T1;\n" + 
					"DROP TABLE IF EXISTS T2;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T1\n" + 
					"SELECT count(*) AS qte, id_revue FROM abonnement GROUP BY id_revue;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T2\n" + 
					"SELECT revue.*, periodicite.libelle FROM revue, periodicite WHERE revue.id_periodicite = periodicite.id_periodicite;\n" + 
					"\n" + 
					"SELECT T1.qte, T2.* FROM T1,T2 WHERE T1.id_revue=T2.id_revue AND T2.id_revue = ?\n" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT 0 AS qte,revue.*, periodicite.libelle FROM revue, periodicite, abonnement WHERE revue.id_periodicite = periodicite.id_periodicite AND revue.id_revue NOT IN (abonnement.id_revue) AND revue.id_revue = ?;");
			requete.setInt(1,id);
			requete.setInt(2, id);
			ResultSet res = requete.executeQuery();

			
			if(res.next()) {
				Revue revue = new Revue(res.getInt("id_revue"),res.getString("titre"),res.getString("description"),res.getDouble("tarif_numero"),res.getString("visuel"),new Periodicite(res.getInt("id_periodicite"),res.getString("libelle")),res.getInt("qte")); 
				if (requete != null)requete.close();
				if (laConnexion != null)laConnexion.close();
				return revue;

			}
			else {
				if (requete != null)requete.close();
				if (laConnexion != null)laConnexion.close();
				throw (new IllegalArgumentException("Aucun objet ne possï¿½de cet identifiant"));
			}
		}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;
				}
	}
	
	@Override
	public Revue getByTitre(Revue revue) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("DROP TABLE IF EXISTS T1;\n" + 
					"DROP TABLE IF EXISTS T2;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T1\n" + 
					"SELECT count(*) AS qte, id_revue FROM abonnement GROUP BY id_revue;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T2\n" + 
					"SELECT revue.*, periodicite.libelle FROM revue, periodicite WHERE revue.id_periodicite = periodicite.id_periodicite;\n" + 
					"\n" + 
					"SELECT T1.qte, T2.* FROM T1,T2 WHERE T1.id_revue=T2.id_revue AND revue.titre = ?\n" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT 0 AS qte,revue.*, periodicite.libelle FROM revue, periodicite, abonnement WHERE revue.id_periodicite = periodicite.id_periodicite AND revue.id_revue NOT IN (abonnement.id_revue) AND revue.titre = ?;");
			requete.setString(1,revue.getTitre());
			requete.setString(2, revue.getTitre());
			ResultSet res = requete.executeQuery();
			if(res.next()) {
				Revue Rrevue = new Revue(res.getInt("id_revue"),res.getString("titre"),res.getString("description"),res.getDouble("tarif_numero"),res.getString("visuel"),new Periodicite(res.getInt("id_periodicite"),res.getString("libelle")),res.getInt("qte")); 
				if (requete != null)requete.close();
				if (laConnexion != null)laConnexion.close();
				return Rrevue;
				}
			else {
				if (requete != null)requete.close();
				if (laConnexion != null)laConnexion.close();
				throw (new IllegalArgumentException("Aucun objet ne possï¿½de cet identifiant"));
			}			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;

				}
	}



	@Override
	public ArrayList<Revue> GetByPerio(Revue revue) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("DROP TABLE IF EXISTS T1;\n" + 
					"DROP TABLE IF EXISTS T2;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T1\n" + 
					"SELECT count(*) AS qte, id_revue FROM abonnement GROUP BY id_revue;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T2\n" + 
					"SELECT revue.*, periodicite.libelle FROM revue, periodicite WHERE revue.id_periodicite = periodicite.id_periodicite;\n" + 
					"\n" + 
					"SELECT T1.qte, T2.* FROM T1,T2 WHERE T1.id_revue=T2.id_revue\n AND T2.id_periodicite = ?" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT 0 AS qte,revue.*, periodicite.libelle FROM revue, periodicite, abonnement WHERE revue.id_periodicite = periodicite.id_periodicite AND revue.id_revue NOT IN (abonnement.id_revue) AND Revue.id_periodicite = ?;");
			requete.setInt(1,revue.getPerio().getId());
			requete.setInt(2, revue.getPerio().getId());
			ResultSet res = requete.executeQuery();
			

			ArrayList<Revue> Liste = new ArrayList<Revue>();
			
			while (res.next()) {
				Liste.add(new Revue(res.getInt("id_revue"),res.getString("titre"),res.getString("description"),res.getDouble("tarif_numero"),res.getString("visuel"),new Periodicite(res.getInt("id_periodicite"),res.getString("libelle")),res.getInt("qte")));
				}
			if (requete != null)requete.close();
			if (laConnexion != null)laConnexion.close();
			return Liste;
			
			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;
				}
	}

	@Override
	public ArrayList<Revue> findAll() {
		try {
			
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("DROP TABLE IF EXISTS T1;\n" + 
					"DROP TABLE IF EXISTS T2;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T1\n" + 
					"SELECT count(*) AS qte, id_revue FROM abonnement GROUP BY id_revue;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T2\n" + 
					"SELECT revue.*, periodicite.libelle FROM revue, periodicite WHERE revue.id_periodicite = periodicite.id_periodicite;\n" + 
					"\n" + 
					"SELECT T1.qte, T2.* FROM T1,T2 WHERE T1.id_revue=T2.id_revue\n" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT 0 AS qte,revue.*, periodicite.libelle FROM revue, periodicite, abonnement WHERE revue.id_periodicite = periodicite.id_periodicite AND revue.id_revue NOT IN (abonnement.id_revue);");
			ResultSet res = requete.executeQuery();
			ArrayList<Revue> array = new ArrayList<Revue>();
			
			while (res.next()) {
				array.add(new Revue(res.getInt("id_revue"),res.getString("titre"),res.getString("description"),res.getDouble("tarif_numero"),res.getString("visuel"),new Periodicite(res.getInt("id_periodicite"),res.getString("libelle")),res.getInt("qte")));
			}				
			if (requete != null)requete.close();
			if (laConnexion != null)laConnexion.close();
			return array;
			
			}catch(SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;
			}
	}

	@Override
	public ArrayList<Revue> Classement_periodicite() {
		try {
			
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("DROP TABLE IF EXISTS T1;\n" + 
					"DROP TABLE IF EXISTS T2;\n" + 
					"DROP TABLE IF EXISTS T3;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T1\n" + 
					"SELECT count(*) AS qte, id_revue FROM abonnement GROUP BY id_revue;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T2\n" + 
					"SELECT revue.*, periodicite.libelle FROM revue, periodicite WHERE revue.id_periodicite = periodicite.id_periodicite;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T3\n" + 
					"SELECT T1.qte, T2.* FROM T1,T2 WHERE T1.id_revue=T2.id_revue\n" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT 0 AS qte,revue.*, periodicite.libelle FROM revue, periodicite, abonnement WHERE revue.id_periodicite = periodicite.id_periodicite AND revue.id_revue NOT IN (abonnement.id_revue);\n" + 
					"\n" + 
					"SELECT * FROM T3 ORDER BY id_periodicite");
			ResultSet res = requete.executeQuery();
			ArrayList<Revue> array = new ArrayList<Revue>();
			
			while (res.next()) {
				array.add(new Revue(res.getInt("id_revue"),res.getString("titre"),res.getString("description"),res.getDouble("tarif_numero"),res.getString("visuel"),new Periodicite(res.getInt("id_periodicite"),res.getString("libelle")),res.getInt("qte")));
			}				
			if (requete != null)requete.close();
			if (laConnexion != null)laConnexion.close();
			return array;
			
			}catch(SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;
			}
	}

	@Override
	public ArrayList<Revue> GetByTarif(Revue revue) {
		try {
			
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("DROP TABLE IF EXISTS T1;\n" + 
					"DROP TABLE IF EXISTS T2;\n" + 
					"DROP TABLE IF EXISTS T3;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T1\n" + 
					"SELECT count(*) AS qte, id_revue FROM abonnement GROUP BY id_revue;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T2\n" + 
					"SELECT revue.*, periodicite.libelle FROM revue, periodicite WHERE revue.id_periodicite = periodicite.id_periodicite;\n" + 
					"\n" + 
					"CREATE TEMPORARY TABLE T3\n" + 
					"SELECT T1.qte, T2.* FROM T1,T2 WHERE T1.id_revue=T2.id_revue\n" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT 0 AS qte,revue.*, periodicite.libelle FROM revue, periodicite, abonnement WHERE revue.id_periodicite = periodicite.id_periodicite AND revue.id_revue NOT IN (abonnement.id_revue);\n" + 
					"\n" + 
					"SELECT * FROM T3 where tarif_numero<= ?");
			requete.setDouble(1,revue.getTarif_numero());
			ResultSet res = requete.executeQuery();
			ArrayList<Revue> array = new ArrayList<Revue>();
			
			while (res.next()) {
				array.add(new Revue(res.getInt("id_revue"),res.getString("titre"),res.getString("description"),res.getDouble("tarif_numero"),res.getString("visuel"),new Periodicite(res.getInt("id_periodicite"),res.getString("libelle"))));
			}				
			if (requete != null)requete.close();
			if (laConnexion != null)laConnexion.close();
			return array;
			
			}catch(SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;
			}
	}

	
	
}
