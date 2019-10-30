package metier;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import factory.DAOFactory;

public class Abonnement {

		private int id_client;
		private int id_revue;
		private LocalDate date_debut;
		private LocalDate date_fin;
	
		public Abonnement(int id_client,int id_revue,String date_debut,String date_fin){
			this.setId_client(id_client);
			this.setId_revue(id_revue);
			
			DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				
			
			this.setDate_debut(LocalDate.parse(date_debut, formatage));
			this.setDate_fin(LocalDate.parse(date_fin, formatage));
		}
		
		public Abonnement(int id_client,int id_revue,LocalDate date_debut,LocalDate date_fin){
			this.setId_client(id_client);
			this.setId_revue(id_revue);
			this.setDate_debut(date_debut);
			this.setDate_fin(date_fin);
		}



		public int getId_client() {
			return id_client;
		}



		public void setId_client(int id_client) {
			this.id_client = id_client;
		}



		public int getId_revue() {
			return id_revue;
		}



		public void setId_revue(int id_revue) {
			this.id_revue = id_revue;
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
			return "Abonnement [id_client=" + id_client + ", id_revue=" + id_revue + ", date_debut=" + date_debut
					+ ", date_fin=" + date_fin + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id_client;
			result = prime * result + id_revue;
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
			if (id_client != other.id_client)
				return false;
			if (id_revue != other.id_revue)
				return false;
			return true;
		}
		
}
