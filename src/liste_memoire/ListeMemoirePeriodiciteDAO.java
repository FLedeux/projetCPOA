package liste_memoire;

import java.util.ArrayList;

import dao.PeriodiciteDAO;
import factory.DAOFactory;
import factory.Persistance;
import metier.Periodicite;
import metier.Revue;

public class ListeMemoirePeriodiciteDAO implements PeriodiciteDAO{

	private static ListeMemoirePeriodiciteDAO instance;

	private ArrayList<Periodicite> donnees;
	
	
	public static ListeMemoirePeriodiciteDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoirePeriodiciteDAO();
		}

		return instance;
	}
	
	private ListeMemoirePeriodiciteDAO() {

		this.donnees = new ArrayList<Periodicite>();

		this.donnees.add(new Periodicite(1, "Mensuel"));
		this.donnees.add(new Periodicite(2, "Quotidien"));
	}
	
	
	
	
	
	
	
	@Override
	public Periodicite getById(int id) {
		
		int idx = this.donnees.indexOf(new Periodicite(id, null));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne poss�de cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public boolean create(Periodicite object) {
		object.setId(0);
		if(this.donnees.contains(object)) {
			throw new IllegalArgumentException("Cette pr�riodicit� existe d�j�");
		}
		
		object.setId(3);
		
		while (this.donnees.contains(object)) {

			object.setId(object.getId() + 1);
			System.out.println("test");
		}
		boolean ok = this.donnees.add(object);		
		return ok;
	}


	@Override
	public boolean update(Periodicite object) {
		int temp = object.getId();
		object.setId(0);
		if(this.donnees.contains(object)) {
			throw new IllegalArgumentException("Cette pr�riodicit� existe d�j�");
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
	public boolean delete(Periodicite object) {
		
		ArrayList<Revue> revue= DAOFactory.getDAOFactory(Persistance.ListeMemoire).getRevueDAO().GetByPerio(new Revue(0,"","",0,"",object)); 
		
		if(revue.size()!=0) {
			throw new IllegalArgumentException("Impossible de supprimer une p�riodicit� utiliser par une revue");
		}
		
		Periodicite supprime;
		
		int idx = this.donnees.indexOf(object);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");
		} else {
			supprime = this.donnees.remove(idx);
		}
		
		return object.equals(supprime);
	}

	@Override
	public Periodicite getByNom(Periodicite periodicite) {
		int i=0;
		while((i<this.donnees.size())&&(this.donnees.get(i).getNom()!=periodicite.getNom())) {
			i++;
		}
		if(i>=this.donnees.size()) {
			throw new IllegalArgumentException("Aucun objet ne poss�de ce nom");
		}
		return this.donnees.get(i);
	}
	
	public ArrayList<Periodicite> findall(){
		return donnees;
	}

	@Override
	public ArrayList<Periodicite> findAll() {
		 return this.donnees;
	}

}
