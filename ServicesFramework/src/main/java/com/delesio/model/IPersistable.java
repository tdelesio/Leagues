package com.delesio.model;

import java.io.Serializable;

public interface IPersistable {

	/**
	 * Create a test object
	 * 
	 * @return Persistable
	 */
	public IPersistable createTestObject();
	
	/**
	 * Return the object primary key
	 * 
	 * @return Serializable
	 */
	public Serializable getPrimaryKey();
	
	public long getId();
	public void setId(long id);
	
}
