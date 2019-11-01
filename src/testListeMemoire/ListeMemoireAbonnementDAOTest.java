package testListeMemoire;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import factory.DAOFactory;
import factory.Persistance;
import metier.Abonnement;


class ListeMemoireAbonnementDAOTest {

	private DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire) ;
	private Abonnement test;
	
	@Test
	void test_create()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/09/2000");
		assertTrue(this.daos.getAbonnementDAO().create(test));
	}
	
	@Test
	void test_update()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/04/2000");
		this.daos.getAbonnementDAO().create(test);
		Abonnement test2 = new Abonnement(test.getClient(),test.getRevue(),"01/05/2003","05/09/2009");
		assertTrue(this.daos.getAbonnementDAO().update(test2));
	}
	
	@Test
	void test_delete()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/06/2000");
		this.daos.getAbonnementDAO().create(test);
		assertTrue(this.daos.getAbonnementDAO().delete(test));
	}
	
	@Test
	void test_GetByIdClientRevue()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2001");
		this.daos.getAbonnementDAO().create(test);
		assertEquals(this.daos.getAbonnementDAO().GetByClientEtRevue(test),test);
	}
	
	@Test
	void test_GetByClient()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(daos.getClientDAO().getById(2),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2005");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByIDClient(array.get(0)),array);
	}
	
	@Test
	void test_GetByRevue()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(2),"02/03/2000","02/05/2050");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByIDRevue(array.get(0)),array);
	}
	@Test
	void test_GetBydate_debut()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"01/01/2019","02/01/2019");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByDateDebut(array.get(0)),array);
	}
	@Test
	void test_GetBydate_fin()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2019");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByDateFin(array.get(0)),array);
	}
	
	
	
	
	
	@Test
	void test_create_deja_existant()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2000");
		this.daos.getAbonnementDAO().create(test);
		try {
			this.daos.getAbonnementDAO().create(test);
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	void test_update_Revue_manquante()
	{
		try {
			this.daos.getAbonnementDAO().update(new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(-1),"02/03/2000","02/05/2000"));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	void test_delete_Revue_manquante()
	{
		try {
			this.daos.getAbonnementDAO().delete(new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(-1),"02/03/2000","02/05/2000"));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	void test_GetByIdClientRevue_Revue_manquant()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2000");
		this.daos.getAbonnementDAO().create(test);
		try {
			this.daos.getAbonnementDAO().GetByClientEtRevue(new Abonnement(test.getClient(),daos.getRevueDAO().getById(-1),test.getDate_debut(),test.getDate_fin()));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);

		}
	}
	
	
	
	
	
	@Test
	void test_update_Client_manquante()
	{
		try {
			this.daos.getAbonnementDAO().update(new Abonnement(daos.getClientDAO().getById(-1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2000"));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	void test_delete_Client_manquante()
	{
		try {
			this.daos.getAbonnementDAO().delete(new Abonnement(daos.getClientDAO().getById(-1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2000"));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	void test_GetByIdClientRevue_Client_manquant()
	{
		test = new Abonnement(daos.getClientDAO().getById(1),daos.getRevueDAO().getById(1),"02/03/2000","02/05/2000");
		this.daos.getAbonnementDAO().create(test);
		try {
			this.daos.getAbonnementDAO().GetByClientEtRevue(new Abonnement(daos.getClientDAO().getById(-1),test.getRevue(),test.getDate_debut(),test.getDate_fin()));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}

	}
	

}
