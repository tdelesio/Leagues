package info.makeyourpicks.model;

import com.delesio.model.AbstractPersistantObject;
import com.delesio.model.IPersistable;

public class Ad extends AbstractPersistantObject {

	private String imageURL;
	private String linkURL;
	private int numberClicks;
	private int paidClicks;
	private String email;
	
	@Override
	public IPersistable createTestObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public int getNumberClicks() {
		return numberClicks;
	}

	public void setNumberClicks(int numberClicks) {
		this.numberClicks = numberClicks;
	}

	public int getPaidClicks() {
		return paidClicks;
	}

	public void setPaidClicks(int paidClicks) {
		this.paidClicks = paidClicks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
