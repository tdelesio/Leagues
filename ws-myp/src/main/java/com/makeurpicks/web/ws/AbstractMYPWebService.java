package com.makeurpicks.web.ws;

import com.delesio.web.ws.AbstractWebService;

public abstract class AbstractMYPWebService extends AbstractWebService {

	protected long getPlayerIdFromSecurityContext()
	{
		return 1;
	}
}
