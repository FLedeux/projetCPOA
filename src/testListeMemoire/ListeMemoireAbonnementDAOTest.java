package testListeMemoire;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import factory.DAOFactory;
import factory.Persistance;
import metier.Abonnement;
import metier.Client;
import metier.Periodicite;
import metier.Revue;


class ListeMemoireAbonnementDAOTest {

	private DAOFactory daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire) ;
	private Abonnement test;
	private Client c;
	private Revue r;
	
	@BeforeEach
	public void before() {
		c= new Client(0,"Jean","Jean","53","rue de Jean","57000","JeanVille","JeanLand");
		r = new Revue(0,"jeanrevue","jean",3.5,"jean.pnj",new Periodicite(1,"test"));
		daos.getClientDAO().create(c);
		daos.getRevueDAO().create(r);
	}
	
	@Test
	void test_create()
	{
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		assertTrue(this.daos.getAbonnementDAO().create(test));
		this.daos.getAbonnementDAO().delete(test);
	}
	
	@Test
	void test_update()
	{
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		this.daos.getAbonnementDAO().create(test);
		Abonnement test2 = new Abonnement(test.getClient(),test.getRevue(),"01/05/2003","05/09/2009");
		assertTrue(this.daos.getAbonnementDAO().update(test2));
		this.daos.getAbonnementDAO().delete(test);
	}
	
	@Test
	void test_delete()
	{
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		this.daos.getAbonnementDAO().create(test);
		assertTrue(this.daos.getAbonnementDAO().delete(test));
	}
	
	@Test
	void test_GetByIdClientRevue()
	{
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		this.daos.getAbonnementDAO().create(test);
		assertEquals(this.daos.getAbonnementDAO().GetByClientEtRevue(test),test);
		this.daos.getAbonnementDAO().delete(test);
	}
	
	@Test
	void test_GetByClient()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByIDClient(array.get(0)),array);
		this.daos.getAbonnementDAO().delete(test);
	}
	
	@Test
	void test_GetByRevue()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByIDRevue(array.get(0)),array);
		this.daos.getAbonnementDAO().delete(test);

	}
	@Test
	void test_GetBydate_debut()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByDateDebut(array.get(0)),array);
		this.daos.getAbonnementDAO().delete(test);
	}
	@Test
	void test_GetBydate_fin()
	{
		ArrayList<Abonnement> array= new ArrayList<Abonnement>();
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		array.add(test);
		this.daos.getAbonnementDAO().create(test);
		assertEquals(daos.getAbonnementDAO().GetByDateFin(array.get(0)),array);
		this.daos.getAbonnementDAO().delete(test);
	}
	
	
	
	
	
	@Test
	void test_create_deja_existant()
	{
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		this.daos.getAbonnementDAO().create(test);
		try {
			this.daos.getAbonnementDAO().create(test);
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
		this.daos.getAbonnementDAO().delete(test);
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
		try {
			test = new Abonnement(c,new Revue(-1,"","",0.0,"",new Periodicite(-1,"")),"02/03/2000","02/09/2000");
			this.daos.getAbonnementDAO().create(test);
			this.daos.getAbonnementDAO().GetByClientEtRevue(new Abonnement(test.getClient(),daos.getRevueDAO().getById(-1),test.getDate_debut(),test.getDate_fin()));
			fail();
		}catch(Exception e) {
			assertTrue(true);
		}
		this.daos.getAbonnementDAO().delete(test);
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
		test = new Abonnement(c,r,"02/03/2000","02/09/2000");
		this.daos.getAbonnementDAO().create(test);
		try {
			this.daos.getAbonnementDAO().GetByClientEtRevue(new Abonnement(daos.getClientDAO().getById(-1),test.getRevue(),test.getDate_debut(),test.getDate_fin()));
			fail();
		}catch(IllegalArgumentException e) {
			assertTrue(true);
		}
		this.daos.getAbonnementDAO().delete(test);

	}
	
	@AfterEach
	public void after() {
		daos.getClientDAO().delete(c);
		daos.getRevueDAO().delete(r);
	}
	

}
