package com.delesio.dao.hibernate;



import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.delesio.dao.IDao;

public class Dao extends AbstractDao implements IDao {

	/**
	 * Load an Object from the database by primary key
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return Object
	 */
	public Object loadObject(Class entityClass, Serializable primaryKey) {
		return hibernateTemplate.get(entityClass, primaryKey);
	}	
	
	/**
	 * Load an Object from the database by entity name and primary key
	 * 
	 * @param entityName
	 * @param primaryKey
	 * @return Object
	 */
	public Object loadObject(String entityName, Serializable primaryKey){
		return hibernateTemplate.get(entityName, primaryKey);
	}
	
	/**
	 * Load all Objects from the database
	 * 
	 * @param entityClass
	 */
    public List loadAllObjects(Class entityClass) {
    	return hibernateTemplate.loadAll(entityClass);
    }
    
    public void merge(Object object)
    {
    	hibernateTemplate.merge(object);
    }
    
    /**
	 * Save an Object in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void save(Object object) {
		hibernateTemplate.save(object);
	}
	
	/**
	 * Save an Object in the database by entity name
	 * 
	 * @param entityName
	 * @param object
	 */
	public void save(String entityName, Object object) {
		hibernateTemplate.save(entityName, object);		
	}

	/**
	 * Save all Objects in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveAllObjects(Collection objects) {
		for(Iterator it = objects.iterator(); it.hasNext();) 
			hibernateTemplate.save(it.next());		
	}

	/**
	 * Save or Update an Object in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveOrUpdate(Object object) {
		hibernateTemplate.saveOrUpdate(object);			
	}
	
	/**
	 * Save or Update an Object in the database by entity name
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveOrUpdate(String entityName, Object object) {
		hibernateTemplate.saveOrUpdate(entityName, object);			
	}

	/**
	 * Save or Update all Objects in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public void saveOrUpdateAllObjects(Collection objects) {
		hibernateTemplate.saveOrUpdateAll(objects);			
	}

	 /**
	 * Delete Object from the database
	 * 
	 * @param object
	 */
    public void deleteObject(Object object) {
    	hibernateTemplate.delete(object);
    }
        
    /**
	 * Delete Object from the database by entity name
	 * 
	 * @param entityName
	 * @param object
	 */
    public void deleteObject(String entityName, Object object) {
    	hibernateTemplate.delete(entityName, object);
    }
	
	/**
	 * Delete All Objects from the database
	 * 
	 * @param objects
	 */
	public void deleteAllObjects(Collection objects){
		hibernateTemplate.deleteAll(objects);
	}
	
	public void updateObject(Object object)
	{
		hibernateTemplate.update(object);
	}
	    
}