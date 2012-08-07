package info.makeyourpicks.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JoinLeagueFilter implements Filter
{

	public void destroy()
	{
	// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();		
		session.setAttribute("leagueName", request.getParameter("leagueName"));
		session.setAttribute("leaguePassword", request.getParameter("leaguePassword"));
		chain.doFilter(req, resp);

	}

	public void init(FilterConfig arg0) throws ServletException
	{
	// TODO Auto-generated method stub

	}

}
