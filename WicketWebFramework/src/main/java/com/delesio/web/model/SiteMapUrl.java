package com.delesio.web.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SiteMapUrl<T> implements Serializable  {

    // change frequency enums
    public enum ChangeFreq {
        ALWAYS, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY, NEVER
    }

    private String loc;
    private String domain;
    private String priority;
    private Date lastMod = new Date(System.currentTimeMillis());
    private ChangeFreq changeFreq;
    private Class<T> urlClass;

    // constructor
    public SiteMapUrl() {

    }

    
    public String getLoc() {
		return loc;
	}


	public Class<T> getUrlClass() {
		return urlClass;
	}


	/**
     * Returns the full url by appending the domain and loc.
     *
     * @return String
     */
    public String getFullURL() {
            return new StringBuilder().append(domain).append(loc).toString();
    }

    /**
     * Returns the lastMod Date as a String.
     *
     * @return String
     */
    public String getLastModAsString() {
            return new SimpleDateFormat("yyyy-MM-dd").format(lastMod);
    }

    /**
     * Returns the changeFreq as a String.
     *
     * @return String
     */
    public String getChangeFreqAsString() {
            return changeFreq.toString().toLowerCase();
    }


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public Date getLastMod() {
		return lastMod;
	}


	public void setLastMod(Date lastMod) {
		this.lastMod = lastMod;
	}


	public ChangeFreq getChangeFreq() {
		return changeFreq;
	}


	public void setChangeFreq(ChangeFreq changeFreq) {
		this.changeFreq = changeFreq;
	}


	public void setLoc(String loc) {
		this.loc = loc;
	}


	public void setUrlClass(Class<T> urlClass) {
		this.urlClass = urlClass;
	}
    
    
}

