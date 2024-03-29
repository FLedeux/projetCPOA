package liste_memoire;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.AbonnementDAO;
import metier.Abonnement;

public class ListeMemoireAbonnementDAO implements AbonnementDAO{

	
	private static ListeMemoireAbonnementDAO instance;

	private ArrayList<Abonnement> donnees;
	
	
	public static ListeMemoireAbonnementDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoireAbonnementDAO();
		}

		return instance;
	}
	
	private ListeMemoireAbonnementDAO() {
		this.donnees = new ArrayList<Abonnement>();
	}
	
	
	
	@Override
	public boolean create(Abonnement object) {
		
       if(this.donnees.contains(object)) {
    	   throw new IllegalArgumentException("cet abonnement existe d�j�");
       }
		return this.donnees.add(object);
		
	}

	@Override
	public boolean update(Abonnement object) {
		
		
		int idx = this.donnees.indexOf(object);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");
		} else {
			
			this.donnees.set(idx, object);
		}
		
		return true;
	}

	@Override
	public boolean delete(Abonnement object) {
		Abonnement supprime;
		
		int idx = this.donnees.indexOf(object);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");
		} else {
			supprime = this.donnees.remove(idx);
		}
		
		return object.equals(supprime);
	}

	@Override
	public ArrayList<Abonnement> GetByIDClient(Abonnement abonnement) {
		int i=0;
		ArrayList<Abonnement> array = new ArrayList<Abonnement>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getClient().equals(abonnement.getClient())) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Abonnement> GetByIDRevue(Abonnement abonnement) {
		int i=0;
		ArrayList<Abonnement> array = new ArrayList<Abonnement>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getRevue().equals(abonnement.getRevue())) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public Abonnement GetByClientEtRevue(Abonnement abonnement) {
		int idx = this.donnees.indexOf(abonnement);
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public ArrayList<Abonnement> GetByDateDebut(Abonnement abonnement) {
		int i=0;
		ArrayList<Abonnement> array = new ArrayList<Abonnement>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getDate_debut()==abonnement.getDate_debut()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Abonnement> GetByDateFin(Abonnement abonnement) {
		int i=0;
		ArrayList<Abonnement> array = new ArrayList<Abonnement>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getDate_fin()==abonnement.getDate_fin()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Abonnement> findAll() {
		return this.donnees;
	}

	@Override
	public ArrayList<Abonnement> Abonnement_en_cours() {
		ArrayList<Abonnement> Liste = new ArrayList<Abonnement>();
		for(int i=0;i<donnees.size();i++) {
			if((donnees.get(i).getDate_debut().isBefore(LocalDate.now()))&&donnees.get(i).getDate_fin().isAfter(LocalDate.now()))
				Liste.add(donnees.get(i));
		}
		return Liste;
	}
	
	}


