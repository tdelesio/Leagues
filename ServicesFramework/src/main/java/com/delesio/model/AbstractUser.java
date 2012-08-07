package com.delesio.model;

public abstract class AbstractUser extends AbstractCredentials {


	protected String firstName;
	protected String lastName;
	protected String email;
	protected Address address=new Address();
	
	
	public AbstractUser()
	{
		super();
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getFullName()
	{
		return firstName+" "+lastName;
	}
}
