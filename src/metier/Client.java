package metier;

import java.util.Scanner;

import factory.DAOFactory;
import liste_memoire.ListeMemoireClientDAO;

public class Client implements Comparable<Client>{
	private int id;
	private String nom;
	private String prenom;
	private String no_rue;
	private String voie;
	private String code_postal;
	private String ville;
	private String pays;
	
	public Client(int id, String nom, String prenom, String no_rue, String voie, String code_postal, String ville, String pays) {
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setNo_rue(no_rue);
		this.setVoie(voie);
		this.setCode_postal(code_postal);
		this.setVille(ville);
		this.setPays(pays);	
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNo_rue() {
		return no_rue;
	}
	public void setNo_rue(String no_rue) {
		this.no_rue = no_rue;
	}
	public String getVoie() {
		return voie;
	}
	public void setVoie(String voie) {
		this.voie = voie;
	}
	public String getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}

	@Override
	public String toString() {
		return  nom + " " + prenom + " au "+ no_rue + " " + voie + " ," + code_postal + " ," + ville + " ," + pays;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if ((id == other.id)||((nom==other.nom)&&(prenom==other.prenom)&&(no_rue==other.no_rue)&&(voie==other.voie)&&(code_postal==other.code_postal)&&(ville==other.ville)&&(pays==other.pays)))
			return true;
		return false;
	}

	@Override
	public int compareTo(Client arg0) {
		int value;
		
		if(ListeMemoireClientDAO.getmethodecomp()) {//cas ou on veux que la liste sois trier en prenant compte de la ville
			value = ville.compareTo(arg0.ville);
			if(value!=0)
				return value;
		}
		
		value = nom.compareTo(arg0.nom);
		if(value!=0)
			return value;
		
		value=prenom.compareTo(arg0.prenom);
		return value;
	}
	

}
