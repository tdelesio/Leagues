package info.makeyourpicks.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class Ticket extends AbstractPersistantObject implements Authentication {

	private Player player;
	private boolean authenticated;
	private String tgt;
	private long createdDate = System.currentTimeMillis();
	
	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority>authorities = new HashSet<GrantedAuthority>();
		GrantedAuthority userAuthority = new GrantedAuthority(){

			@Override
			public String getAuthority() {
				return "user";
			}
			
		};
		authorities.add(userAuthority);
		if (player.getMemberLevel()<=2)
		{
			GrantedAuthority leagueAdminAuthority = new GrantedAuthority(){

				@Override
				public String getAuthority() {
					return "league_admin";
				}
				
			};
			authorities.add(leagueAdminAuthority);
		}
		if (player.getMemberLevel()==1)
		{
			GrantedAuthority adminAuthority = new GrantedAuthority(){

				@Override
				public String getAuthority() {
					return "admin";
				}
				
			};
			authorities.add(adminAuthority);
		}
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return player.getPassword();
	}

	@Override
	public Object getDetails() {
		return player;
	}

	@Override
	public Object getPrincipal() {
		return player.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		this.authenticated = arg0;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getTgt() {
		return tgt;
	}

	public void setTgt(String tgt) {
		this.tgt = tgt;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	
}
