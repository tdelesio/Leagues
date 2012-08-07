package com.delesio.beans.factory.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class JndiPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer
{

	private List locations;
	
	/**
	 * Load properties into the given instance.
	 * @param props the Properties instance to load into
	 * @throws java.io.IOException in case of I/O errors
	 * @see #setLocations
	 */
	protected void loadProperties(Properties props) throws IOException {
		String propertiesHome;
		
//		try
//		{
//			propertiesHome = StringNSBHelper.getPropertiesHomeDirectory();
//		}
//		catch(Exception exception)
//		{
//			throw new RuntimeException("Unable to load the properties home location from JNDI");
//		}
		String value;
		for(Iterator it = locations.iterator(); it.hasNext();)
		{
			//props.load(new FileInputStream(propertiesHome+it.next().toString()));
			value = it.next().toString();
			if (value.startsWith("classpath:/"))
			{
				value = value.substring(value.indexOf("classpath:/"));
				props.load(getClass().getResourceAsStream(value));
			}
			else if (value.startsWith("jndi:/"))
			{
				try
				{
					Context context = new InitialContext();
					value = (String)context.lookup("string/dbproperties");
					props.load(new FileInputStream(value));
				}
				catch (NamingException exception)
				{
					exception.printStackTrace();
				}
				
			}
			else
			{
				props.load(new FileInputStream(value));
			}
		}
//			props.load(new classpath)
	}	
	
	// getters and setters
	public List getLocations() {
		return locations;
	}
	public void setLocations(List locations) {
		this.locations = locations;
	}
}
