package com.makeurpicks.web.ws;

import info.makeyourpicks.model.Ticket;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.delesio.web.ws.AbstractWebService;

public abstract class AbstractMYPWebService extends AbstractWebService {

	protected long getPlayerIdFromSecurityContext()
	{
		Ticket authentication = (Ticket)SecurityContextHolder.getContext().getAuthentication();
		if (authentication!=null&&authentication.getPlayer()!=null)
			return authentication.getPlayer().getId();
		else
			return 0;
	}
}
