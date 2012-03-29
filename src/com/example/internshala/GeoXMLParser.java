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

public class GeoXMLParser extends DefaultHandler{

	ArrayList<Float> loc;
	boolean flag = true;
	
	private String tempVal;

	public GeoXMLParser(){
		loc = new ArrayList<Float>();
	}
	
	public ArrayList<Float> runExample(String url) {
		parseDocument(url);
		printData();	
		return loc;
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
		
		Log.e("No of loc-", loc.size()+"");
		Iterator it = loc.iterator();
		while(it.hasNext()) {
			Log.e("",it.next().toString());
		}
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("location")) {
			//add it to the list
			flag = false;
			
		}else if (flag && qName.equalsIgnoreCase("lat")) {
			loc.add(Float.parseFloat(tempVal));
		}else if (flag && qName.equalsIgnoreCase("lng")) {
			loc.add(Float.parseFloat(tempVal));
		}
		
	}
	
	public static void main(String[] args){
		GeoXMLParser spe = new GeoXMLParser();
		spe.runExample("http://www.internfair.internshala.com/internFiles/AppDesign/GarageList.xml");
	}
	
	
}



