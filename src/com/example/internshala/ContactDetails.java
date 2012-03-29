package com.example.internshala;

public class ContactDetails {

	/**
	 * @param args
	 */
	
	private String person;
	private String landline;
	private String mobile;
	private String email;
	
	public ContactDetails(String p, String l, String m, String e)
	{
		this.person = p;
		this.landline = l;
		this.mobile = m;
		this.email = e;
	}
	
	public ContactDetails(){}
	
	public String getPerson() {
		return person;
	}


	public void setPerson(String person) {
		this.person = person;
	}


	public String getLandline() {
		return landline;
	}


	public void setLandline(String landline) {
		this.landline = landline;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
