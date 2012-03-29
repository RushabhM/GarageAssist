package com.example.internshala;

import com.google.android.maps.GeoPoint;

public class Garage {

	/**
	 * @param args
	 */
	
	private String name;
	private String cashless;
	private String manufacturer;
	private Address_ addr;
	private ContactDetails conDetails;
	//private GeoPoint latlng;
	private double distance;
	private double lat;
	private double lng;
	
	public Garage(String n, String c, String m)
	{
		this.name = n;
		this.cashless = c;
		this.manufacturer = m;
		//this.addr = ad;
		//this.conDetails = cd;
	}
	
	public Garage(){}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Garage Details - ");
		sb.append("Name:" + getName());
		sb.append(", ");
		sb.append("Cashless:" + getCashless());
		sb.append(", ");
		sb.append("Manuf:" + getManufacturer());
		sb.append(",\n");
		sb.append("Street:" + getAddr().getStreet());
		sb.append(", ");
		sb.append("City:" + getAddr().getCity());
		sb.append(", ");
		sb.append("Pincode:" + getAddr().getPincode());
		sb.append(", ");
		sb.append("State:" + getAddr().getState());
		sb.append(",\n");
		sb.append("Person:" + getConDetails().getPerson());
		sb.append(", ");
		sb.append("Landline:" + getConDetails().getLandline());
		sb.append(", ");
		sb.append("Mobile:" + getConDetails().getMobile());
		sb.append(", ");
		sb.append("Email:" + getConDetails().getEmail());
		sb.append(", ");
		
		return sb.toString();
	}
	
	
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCashless() {
		return cashless;
	}



	public void setCashless(String cashless) {
		this.cashless = cashless;
	}



	public String getManufacturer() {
		return manufacturer;
	}



	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}



	public Address_ getAddr() {
		return addr;
	}



	public void setAddr(Address_ addr) {
		this.addr = addr;
	}



	public ContactDetails getConDetails() {
		return conDetails;
	}



	public void setConDetails(ContactDetails conDetails) {
		this.conDetails = conDetails;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	/*
	public GeoPoint getLatlng() {
		return latlng;
	}

	public void setLatlng(GeoPoint latlng) {
		this.latlng = latlng;
	}
*/
		

}
