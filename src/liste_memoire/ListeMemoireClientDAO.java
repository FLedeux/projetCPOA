package liste_memoire;

import java.util.ArrayList;
import java.util.Collections;

import dao.ClientDAO;
import factory.DAOFactory;
import factory.Persistance;
import metier.Abonnement;
import metier.Client;


public class ListeMemoireClientDAO implements ClientDAO{

	private static ListeMemoireClientDAO instance;

	private ArrayList<Client> donnees;
	
	private static boolean methodecomp;//true=prise en compte de la ville false =seulement alphabétique
	
	
	public static ListeMemoireClientDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoireClientDAO();
		}

		return instance;
	}
	
	private ListeMemoireClientDAO() {
		this.donnees = new ArrayList<Client>();
	}
	



	@Override
	public Client getById(int id) {
		int idx = this.donnees.indexOf(new Client(id, "","","","","","",""));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possÃ¨de cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public boolean create(Client object) {
		object.setId(0);
		if(this.donnees.contains(object)) {
			throw new IllegalArgumentException("Ce client existe déjà");
		}
		
		object.setId(1);
		
		while (this.donnees.contains(object)) {

			object.setId(object.getId() + 1);
		}
		boolean ok = this.donnees.add(object);
		
		return ok;
	}

	@Override
	public boolean update(Client object) {
		
		int temp = object.getId();
		object.setId(0);
		if(this.donnees.contains(object)) {
			throw new IllegalArgumentException("Ce client existe déjà");
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
	public boolean delete(Client object) {
		
		ArrayList<Abonnement> abonnement= DAOFactory.getDAOFactory(Persistance.ListeMemoire).getAbonnementDAO().GetByIDClient(new Abonnement(object,null,"01/01/2000","01/01/2000")); 
		
		if(abonnement.size()!=0) {
			throw new IllegalArgumentException("Impossible de supprimer un client utiliser par un abonnement");
		}
		
		Client supprime;
		
		int idx = this.donnees.indexOf(object);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");
		} else {
			supprime = this.donnees.remove(idx);
		}
		
		return object.equals(supprime);
	}


	@Override
	public ArrayList<Client> GetByNomPrenom(Client client) {
		int i=0;
		ArrayList<Client> array = new ArrayList<Client>();
		while(i<this.donnees.size()) {
			if ((this.donnees.get(i).getNom().contentEquals(client.getNom()))&&(this.donnees.get(i).getPrenom().equals(client.getPrenom()))) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Client> GetByAdresse(Client client) {
		int i=0;
		ArrayList<Client> array = new ArrayList<Client>();
		while(i<this.donnees.size()) {
			if ((this.donnees.get(i).getNo_rue()==client.getNo_rue())&&(this.donnees.get(i).getVoie()==client.getVoie())) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Client> GetByCode_Postal(Client client) {
		int i=0;
		ArrayList<Client> array = new ArrayList<Client>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getCode_postal()==client.getCode_postal()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Client> GetByVille(Client client) {
		int i=0;
		ArrayList<Client> array = new ArrayList<Client>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getVille()==client.getVille()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Client> GetByPays(Client client) {
		int i=0;
		ArrayList<Client> array = new ArrayList<Client>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getPays()==client.getPays()) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Client> findAll() {
		return this.donnees;
	}

	
	@Override
	public ArrayList<Client> GetByNom(Client client) {
		int i=0;
		ArrayList<Client> array = new ArrayList<Client>();
		while(i<this.donnees.size()) {
			if (this.donnees.get(i).getNom().equals(client.getNom())) {
				array.add(this.donnees.get(i));
			}
			i++;
		}
		return array;
	}

	@Override
	public ArrayList<Client> Trie_Alphabetique() {
		ListeMemoireClientDAO.methodecomp=false;
		ArrayList<Client> donnees = this.donnees;
		Collections.sort(donnees);//regarder pour faire uen autre class de comparateur pour un coup ville et pour l'autre coup: plus propre
		return donnees;
	}

	@Override
	public ArrayList<Client> Trie_Ville() {
		ListeMemoireClientDAO.methodecomp=true;
		ArrayList<Client> donnees = this.donnees;
		Collections.sort(donnees);//regarder pour faire uen autre class de comparateur pour un coup ville et pour l'autre coup: plus propre
		return donnees;
	}

	public static boolean getmethodecomp() {
		return methodecomp;
	}
	
}
