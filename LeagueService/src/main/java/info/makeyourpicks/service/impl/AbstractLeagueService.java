package info.makeyourpicks.service.impl;

import com.delesio.cache.ICacheProvider;
import com.delesio.service.AbstractService;

public abstract class AbstractLeagueService extends AbstractService {

protected ICacheProvider cacheProvider;
    
    public void setCacheProvider(ICacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}
    
}
