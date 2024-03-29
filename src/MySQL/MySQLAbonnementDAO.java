package MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AbonnementDAO;
import factory.DAOFactory;
import factory.Persistance;
import launch.Launch_main;
import metier.Abonnement;

public class MySQLAbonnementDAO implements AbonnementDAO{

private static MySQLAbonnementDAO instance;
	
	private MySQLAbonnementDAO() {}

	public static MySQLAbonnementDAO getInstance() {
		if (instance==null) {
			instance = new MySQLAbonnementDAO();
		}
		return instance;
	}
	
	
	
	@Override
	public boolean create(Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			
			
			PreparedStatement requete = laConnexion.prepareStatement("Select count(*) from Abonnement where  id_client = ? AND id_revue = ?");
			requete.setInt(1, abonnement.getClient().getId());
			requete.setInt(2, abonnement.getRevue().getId());

			
			ResultSet result = requete.executeQuery();
			result.next();
			if (result.getInt(1)>0) {
				throw (new IllegalArgumentException("Cet abonnement existe d�j�"));
			}
			
			requete = laConnexion.prepareStatement("insert into Abonnement (id_client, id_revue, date_debut, date_fin) values(?,?,?,?)");
			requete.setInt(1, abonnement.getClient().getId());
			requete.setInt(2, abonnement.getRevue().getId());
			requete.setDate(3, java.sql.Date.valueOf(abonnement.getDate_debut()));
			requete.setDate(4,java.sql.Date.valueOf(abonnement.getDate_fin()));
			int res = requete.executeUpdate();
			
			if (requete != null)requete.close();
			
			if (laConnexion != null)laConnexion.close();
			return res==1;
			}
			catch (SQLException sqle) {
				throw (new IllegalArgumentException(sqle.getMessage()));
	
			}
	}

	@Override
	public boolean update(Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			
			PreparedStatement requete = laConnexion.prepareStatement("update Abonnement set date_debut=?,date_fin=?  where id_client=? AND id_revue=?");
			
			requete.setDate(1, java.sql.Date.valueOf(abonnement.getDate_debut()));
			requete.setDate(2,java.sql.Date.valueOf(abonnement.getDate_fin()));	
			requete.setInt(3, abonnement.getClient().getId());
			requete.setInt(4, abonnement.getRevue().getId());
			
			int res = requete.executeUpdate();
			
			if (requete != null)requete.close();
			
			if (laConnexion != null)laConnexion.close();
			if(res<1) {
				throw (new IllegalArgumentException("Aucun objet ne poss�de cet identifiant"));
			}
			return res==1;		
			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return false;

				}
	}

	@Override
	public boolean delete(Abonnement abonnement) {

		try {
		Connection laConnexion = Connexion.creeConnexion();
		PreparedStatement requete = laConnexion.prepareStatement("delete from Abonnement where id_client=? and id_revue=?");
		requete.setInt(1, abonnement.getClient().getId());
		requete.setInt(2, abonnement.getRevue().getId());
		int res = requete.executeUpdate();
		if (requete != null)requete.close();
		
		if (laConnexion != null)laConnexion.close();
		if(res<1) {
			throw (new IllegalArgumentException("Aucun objet ne poss�de cet identifiant"));
		}
		return res==1;
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
			return false;

		}
	}

	@Override
	public ArrayList<Abonnement> GetByIDClient(Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement Where id_client=?");
			requete.setInt(1,abonnement.getClient().getId());
			ResultSet res = requete.executeQuery();
			

			ArrayList<Abonnement> Liste = new ArrayList<Abonnement>();
			DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
			while (res.next()) {
				Liste.add(new Abonnement(daos.getClientDAO().getById(res.getInt("id_client")),daos.getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate()));
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
	public ArrayList<Abonnement> GetByIDRevue(Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement Where id_revue=?");
			requete.setInt(1,abonnement.getRevue().getId());
			ResultSet res = requete.executeQuery();
			

			ArrayList<Abonnement> Liste = new ArrayList<Abonnement>();
			
			DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
			while (res.next()) {
				Liste.add(new Abonnement(daos.getClientDAO().getById(res.getInt("id_client")),daos.getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate()));
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
	public Abonnement GetByClientEtRevue( Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement Where id_client=? AND id_revue=?");
			requete.setInt(1,abonnement.getClient().getId());
			requete.setInt(2, abonnement.getRevue().getId());
			ResultSet res = requete.executeQuery();
			
			DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
			if(res.next()) {
				Abonnement abo = new Abonnement(daos.getClientDAO().getById(res.getInt("id_client")),daos.getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate());

				if (requete != null)requete.close();
				if (laConnexion != null)laConnexion.close();
				return abo;			
				}
			else {
				if (requete != null)requete.close();
				if (laConnexion != null)laConnexion.close();
				throw (new IllegalArgumentException("Aucun objet ne poss�de cet identifiant"));
			}
			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				return null;

				}
		
		
	}

	@Override
	public ArrayList<Abonnement> GetByDateDebut(Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement Where date_debut=?");
			requete.setDate(1,java.sql.Date.valueOf(abonnement.getDate_debut()));
			ResultSet res = requete.executeQuery();
			

			ArrayList<Abonnement> Liste = new ArrayList<Abonnement>();
		
			DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
			while (res.next()) {
				Liste.add(new Abonnement(daos.getClientDAO().getById(res.getInt("id_client")),daos.getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate()));
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
	public ArrayList<Abonnement> GetByDateFin(Abonnement abonnement) {
		try {
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement Where date_fin=?");
			requete.setDate(1,java.sql.Date.valueOf(abonnement.getDate_fin()));
			ResultSet res = requete.executeQuery();
			

			ArrayList<Abonnement> Liste = new ArrayList<Abonnement>();
			
			DAOFactory daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
			while (res.next()) {
				Liste.add(new Abonnement(daos.getClientDAO().getById(res.getInt("id_client")),daos.getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate()));
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
	public ArrayList<Abonnement> findAll() {
		try {
			
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement");
			ResultSet res = requete.executeQuery();
			ArrayList<Abonnement> array = new ArrayList<Abonnement>();
			
			while (res.next()) {
				array.add(new Abonnement(Launch_main.getdaos().getClientDAO().getById(res.getInt("id_client")),Launch_main.getdaos().getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate()));
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
	public ArrayList<Abonnement> Abonnement_en_cours() {
try {
			
			Connection laConnexion = Connexion.creeConnexion();
			PreparedStatement requete = laConnexion.prepareStatement("Select * From Abonnement WHERE date_debut <= NOW() AND date_fin >= NOW()");
			ResultSet res = requete.executeQuery();
			ArrayList<Abonnement> array = new ArrayList<Abonnement>();
			
			while (res.next()) {
				array.add(new Abonnement(Launch_main.getdaos().getClientDAO().getById(res.getInt("id_client")),Launch_main.getdaos().getRevueDAO().getById(res.getInt("id_revue")),res.getDate("date_debut").toLocalDate(),res.getDate("date_fin").toLocalDate()));
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
