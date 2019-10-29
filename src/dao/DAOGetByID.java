package dao;

import java.util.ArrayList;

public interface DAOGetByID<T> extends DAO<T>{

	public abstract T getById(int id);
	
	public abstract ArrayList<T> findAll();

}
