package model;

import java.io.Serializable;

public class Address implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1726162697440374993L;
	private String streetNumber;
	private String streetName;
	private String city;
	private String state;
	private String zip;
	
	public Address(String streetNumber, String streetName, String city, String state, String zip) 
	{
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreetNumber() 
	{
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) 
	{
		this.streetNumber = streetNumber;
	}

	public String getStreetName() 
	{
		return streetName;
	}

	public void setStreetName(String streetName) 
	{
		this.streetName = streetName;
	}

	public String getCity() 
	{
		return city;
	}

	public void setCity(String city) 
	{
		this.city = city;
	}

	public String getState() 
	{
		return state;
	}

	public void setState(String state) 
	{
		this.state = state;
	}

	public String getZip() 
	{
		return zip;
	}

	public void setZip(String zip) 
	{
		this.zip = zip;
	}
	
	@Override
	public String toString() 
	{
		return streetNumber + " " + streetName + " " + city + " " + state + "-" + zip;
	}
	
}
