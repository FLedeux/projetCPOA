package metier;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Abonnement {

		private Client client;
		private Revue revue;
		private LocalDate date_debut;
		private LocalDate date_fin;
	
		public Abonnement(Client client, Revue revue,String date_debut,String date_fin){
			this.setClient(client);
			this.setRevue(revue);
			
			DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				
			
			this.setDate_debut(LocalDate.parse(date_debut, formatage));
			this.setDate_fin(LocalDate.parse(date_fin, formatage));
		}
		
		public Abonnement(Client client,Revue revue,LocalDate date_debut,LocalDate date_fin){
			this.setClient(client);
			this.setRevue(revue);
			this.setDate_debut(date_debut);
			this.setDate_fin(date_fin);
		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		public Revue getRevue() {
			return revue;
		}

		public void setRevue(Revue revue) {
			this.revue = revue;
		}

		public LocalDate getDate_debut() {
			return date_debut;
		}



		public void setDate_debut(LocalDate date_debut) {
			this.date_debut = date_debut;
		}



		public LocalDate getDate_fin() {
			return date_fin;
		}



		public void setDate_fin(LocalDate date_fin) {
			this.date_fin = date_fin;
		}



		@Override
		public String toString() {
			return "Abonnement [id_client=" + client + ", id_revue=" + revue + ", date_debut=" + date_debut
					+ ", date_fin=" + date_fin + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((client == null) ? 0 : client.hashCode());
			result = prime * result + ((revue == null) ? 0 : revue.hashCode());
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
			Abonnement other = (Abonnement) obj;
			if (client == null) {
				if (other.client != null)
					return false;
			} else if (!client.equals(other.client))
				return false;
			if (revue == null) {
				if (other.revue != null)
					return false;
			} else if (!revue.equals(other.revue))
				return false;
			if(client.equals(other.client)&&revue.equals(other.revue))
			return true;
			else return false;
		}


		
}
