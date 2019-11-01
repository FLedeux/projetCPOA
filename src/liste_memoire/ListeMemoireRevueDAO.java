package liste_memoire;

import java.util.ArrayList;
import java.util.Collections;

import dao.RevueDAO;
import factory.DAOFactory;
import factory.Persistance;
import metier.Abonnement;
import metier.Client;
import metier.Periodicite;
import metier.Revue;

public class ListeMemoireRevueDAO implements RevueDAO{

	private static ListeMemoireRevueDAO instance;

	private ArrayList<Revue> donnees;
	
	
	
	public static ListeMemoireRevueDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoireRevueDAO();
		}

		return instance;
	}
	
	private ListeMemoireRevueDAO() {

		this.donnees = new ArrayList<Revue>();
	}
	

	@Override
	public Revue getById(int id) {
		
		int idx = this.donnees.indexOf(new Revue(id, "","",0,"",new Periodicite(0,"")));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public boolean create(Revue object) {
		object.setId(0);
		if(this.donnees.contains(object)) {
			throw new IllegalArgumentException("Cette revue existe déjà");
		}
		
		object.setId(1);

		while (this.donnees.contains(object)) {

			object.setId(object.getId() + 1);
		}
		boolean ok = this.donnees.add(object);
		
		return ok;
	}

	@Override
	public boolean update(Revue object) {
		
		int temp = object.getId();
		object.setId(0);
		if(this.donnees.contains(object)) {
			throw new IllegalArgumentException("Cette revue existe déjà");
		}
		object.setId(temp);
		
		int idx = this.donnees.indexOf(object);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");
		} else {
			
			this.donnees.set(idx, object);
		}
		return true;
		
	}

	@Override
	public boolean delete(Revue object) {
		
		ArrayList<Abonnement> abonnement= DAOFactory.getDAOFactory(Persistance.ListeMemoire).getAbonnementDAO().GetByIDRevue(new Abonnement(null,object,"01/01/2000","01/01/2000")); 
		
		if(abonnement.size()!=0) {
			throw new IllegalArgumentException("Impossible de supprimer une périodicité utiliser par une revue");
		}
		
		
		Revue supprime;
		
		int idx = this.donnees.indexOf(object);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");
		} else {
			supprime = this.donnees.remove(idx);
		}
		return object.equals(supprime);
		
	}

	@Override
	public Revue getByTitre(Revue revue) {
		
		int i=0;
		while((i<this.donnees.size())&&(this.donnees.get(i).getTitre()!=revue.getTitre())) {
			i++;
		}
		if(i>=this.donnees.size()) {
			throw new IllegalArgumentException("Aucun objet ne possède ce nom");
		}
		return this.donnees.get(i);
		
	}

	
	@Override
	public ArrayList<Revue> GetByPerio(Revue revue) {
		int i=0;
		ArrayList<Revue> array = new ArrayList<Revue>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getPerio().getId()==revue.getPerio().getId()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
		
	}

	@Override
	public ArrayList<Revue> findAll() {
	return this.donnees;
	}

	@Override
	public ArrayList<Revue> Classement_periodicite() {
		ArrayList<Revue> donnees = this.donnees;
		Collections.sort(donnees);
		return donnees;
	}

	@Override
	public ArrayList<Revue> GetByTarif(Revue revue) {
		int i=0;
		ArrayList<Revue> array = new ArrayList<Revue>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getTarif_numero()<=revue.getTarif_numero()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

}
