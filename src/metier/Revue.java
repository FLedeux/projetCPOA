package metier;


public class Revue {
	private int id;
	private String titre;
	private String description;
	private double tarif_numero;
	private String visuel;
	private Periodicite perio;
	private int quantite;
	
	public Revue(int id, String titre,String description,double tarif_numero,String visuel,Periodicite perio) {
		this.setId(id);
		this.setTitre(titre);
		this.setDescription(description);
		this.setTarif_numero(tarif_numero);
		this.setVisuel(visuel);
		this.setPerio(perio);

	}
	

	public Revue(int id, String titre,String description,double tarif_numero,String visuel,Periodicite perio, int quantite) {
		this.setId(id);
		this.setTitre(titre);
		this.setDescription(description);
		this.setTarif_numero(tarif_numero);
		this.setVisuel(visuel);
		this.setPerio(perio);
		this.setQuantite(quantite);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTarif_numero() {
		return tarif_numero;
	}

	public void setTarif_numero(double tarif_numero) {
		this.tarif_numero = tarif_numero;
	}

	public String getVisuel() {
		return visuel;
	}

	public void setVisuel(String visuel) {
		this.visuel = visuel;
	}

	public Periodicite getPerio() {
		return perio;
	}


	public void setPerio(Periodicite perio) {
		this.perio = perio;
	}


	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return titre + " (" + tarif_numero + " euros)";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Revue other = (Revue) obj;
		if ((id == other.id)||((titre==other.titre)&&(description==other.description)&&(tarif_numero==other.tarif_numero)&&(perio.equals(other.perio))))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
}
