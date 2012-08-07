package com.delesio.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IDao {
	/**
	 * Load an Object from the database by primary key
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return Object
	 */
	public Object loadObject(Class entityClass, Serializable primaryKey);
	
	/**
	 * Load an Object from the database by entity name and primary key
	 * 
	 * @param entityName
	 * @param primaryKey
	 * @return Object
	 */
	public Object loadObject(String entityName, Serializable primaryKey);
	
	/**
	 * Load all Objects from the database
	 * 
	 * @param entityClass
	 */
    public List loadAllObjects(Class entityClass);
    
    /**
	 * Save an Object in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void save(Object object);
	
	/**
	 * Save an Object in the database by entity name
	 * 
	 * @param entityName
	 * @param object
	 */
	public void save(String entityName, Object object);

	/**
	 * Save all Objects in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveAllObjects(Collection objects);

	/**
	 * Save or Update an Object in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveOrUpdate(Object object);
	
	/**
	 * Save or Update an Object in the database by entity name
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveOrUpdate(String entityName, Object object);

	/**
	 * Save or Update all Objects in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveOrUpdateAllObjects(Collection objects);

	 /**
	 * Delete Object from the database
	 * 
	 * @param object
	 */
    public void deleteObject(Object object);
        
    /**
	 * Delete Object from the database by entity name
	 * 
	 * @param entityName
	 * @param object
	 */
    public void deleteObject(String entityName, Object object);
	
	/**
	 * Delete All Objects from the database
	 * 
	 * @param objects
	 */
	public void deleteAllObjects(Collection objects);	
	
	public void merge(Object object);
	public void updateObject(Object object);
}
