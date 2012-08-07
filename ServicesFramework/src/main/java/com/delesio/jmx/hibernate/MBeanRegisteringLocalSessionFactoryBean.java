package com.delesio.jmx.hibernate;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.hibernate.jmx.StatisticsService;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class MBeanRegisteringLocalSessionFactoryBean extends LocalSessionFactoryBean {
	private String _sessionFactoryName;
	 private ObjectName _objectName;
	 private MBeanServer _mbeanServer;

	 @Override
	 protected void afterSessionFactoryCreation() throws Exception {
	   super.afterSessionFactoryCreation();
	   try {
	     // register JMX MBean
//	     _objectName = new ObjectName("Hibernate:type=statistics,application="+getSessionFactoryName());
//	     _mbeanServer = getMBeanServer();

	     final StatisticsService mBean = new StatisticsService();
	     mBean.setSessionFactory(getSessionFactory());
	     _mbeanServer.registerMBean(mBean, _objectName);

	   } catch (final Throwable t) {
//	     log.warn("failed to register MBean for SessionFactory: " + _objectName, t);
	     _objectName = null;
	     _mbeanServer = null;
	   }
	   // if _objectName != null --> MBean was registered
	 }

	 @Override
	 protected void beforeSessionFactoryDestruction() {
	   if (_objectName != null) {
	     try {
	       _mbeanServer.unregisterMBean(_objectName);
	     } catch (final Throwable t) {
//	       log.warn("failed to unregister MBean for SessionFactory: "
//	         + _objectName.getDomain());
	     }
	   }
	 }

	 /* SNIP: getMBeanServer(), getSessionFactoryName(), setSessionFactoryName(String) */


}
