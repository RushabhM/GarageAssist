package com.example.internshala;

public class Address_ {

	/**
	 * @param args
	 */
	
	private String street;
	private String city;
	private String pincode;
	private String state;
	
	public Address_(String str, String c, String p, String st)
	{
		this.street = str;
		this.city = c;
		this.pincode = p;
		this.state = st;
	}
	
	public Address_(){}
	
	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getPincode() {
		return pincode;
	}



	public void setPincode(String pincode) {
		this.pincode = pincode;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
