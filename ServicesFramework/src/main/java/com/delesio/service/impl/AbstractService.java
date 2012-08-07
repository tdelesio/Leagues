package com.delesio.service.impl;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.delesio.cache.ICacheProvider;
import com.delesio.dao.IDao;

public abstract class AbstractService {
	protected IDao dao;	
	protected ICacheProvider cacheProvider;
	
	// the transaction manager
	protected PlatformTransactionManager transactionManager;
	
	// getters and setters
	public IDao getDao() {
		return dao;
	}
	public void setDao(IDao dao) {
		this.dao = dao;
	}
	
	protected abstract void init();
//extends HibernateDaoSupport{
	public ICacheProvider getCacheProvider() {
		return cacheProvider;
	}
	public void setCacheProvider(ICacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
		
	public TransactionTemplate getTransactionTemplate() {
		  TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		  transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
		  return transactionTemplate;
		 }

}
