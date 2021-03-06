package com.example.internshala;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.example.internshala.Garage;

public class GrgXMLParser extends DefaultHandler{

	ArrayList<Garage> myGrg;
	
	private String tempVal;
	
	private Garage tempGrg;
	private Address_ tempAdr;
	private ContactDetails tempCd;
	
	public GrgXMLParser(){
		myGrg = new ArrayList<Garage>();
	}
	
	public ArrayList<Garage> runExample(String url) {
		parseDocument(url);
		printData();	
		return myGrg;
	}

	private void parseDocument(String url) {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
		

			URL sourceUrl = new URL(url);
			
			//parse the file and also register this class for call backs
			sp.parse(new InputSource(sourceUrl.openStream()), this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printData(){
		
		Log.e("No of Garages-", myGrg.size()+"");
		Iterator it = myGrg.iterator();
		while(it.hasNext()) {
			Log.e("",it.next().toString());
		}
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("Garage")) {
			//create a new instance of employee
			tempGrg = new Garage();
			tempGrg.setName(attributes.getValue("Name"));
			tempGrg.setCashless(attributes.getValue("Cashless"));
			tempGrg.setManufacturer(attributes.getValue("Manufacturer"));
		}
		
		else if(qName.equalsIgnoreCase("Address")) {
			//create a new instance of address
			tempAdr = new Address_();
			tempAdr.setStreet(attributes.getValue("Street"));
			tempAdr.setCity(attributes.getValue("City"));
			tempAdr.setPincode(attributes.getValue("PinCode"));
			tempAdr.setState(attributes.getValue("State"));
		}
		
		else if(qName.equalsIgnoreCase("ContactDetails")) {
			tempCd = new ContactDetails();
			tempCd.setPerson(attributes.getValue("ContactPerson"));
			tempCd.setLandline(attributes.getValue("Landline"));
			tempCd.setMobile(attributes.getValue("Mobile"));
			tempCd.setEmail(attributes.getValue("EMail"));
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("Garage")) {
			//add it to the list
			myGrg.add(tempGrg);
			
		}else if (qName.equalsIgnoreCase("Address")) {
			tempGrg.setAddr(tempAdr);
		}else if (qName.equalsIgnoreCase("ContactDetails")) {
			tempGrg.setConDetails(tempCd);
		}
		
	}
	
	public static void main(String[] args){
		GrgXMLParser spe = new GrgXMLParser();
		spe.runExample("http://www.internfair.internshala.com/internFiles/AppDesign/GarageList.xml");
	}
	
	
}



