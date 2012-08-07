package com.delesio.model;

public class Address extends AbstractPersistantObject {

	private String address1;
	private String address2;
	private String city;
	private String stateCode;
	private String zip;
	private String country;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getFullAddress()
	{
		StringBuffer buffer = new StringBuffer(address1);
		buffer.append("\n");
		if (address2!=null)
		{
			buffer.append(address2);
			buffer.append("\n");
		}
		buffer.append(city);
		buffer.append(", ");
		buffer.append(stateCode);
		buffer.append(zip);
		
		return buffer.toString();
	}


	@Override
	public IPersistable createTestObject() {
		Address address = new Address();
		address.setAddress1("street1");
		address.setAddress2("street2");
		address.setCity("city");
		address.setCountry("US");
		address.setStateCode("NJ");
		address.setZip("12345");
		return address;
	}
	

}
