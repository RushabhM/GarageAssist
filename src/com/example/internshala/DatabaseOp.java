package com.example.internshala;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseOp {

	/**
	 * @param args
	 */
	static ArrayList<Garage> garages;
	static Garage temp;
	static String geo_street;
	static String geo_city;
	static String geo_state;
	static GeoPoint latlng;
	static String geo_url;
	static SQLiteDatabase db;
	
	public static void initialize(Context context)
	{
		//Open a SQLite Database
	
		
		db = context.openOrCreateDatabase("berkshire.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		//Check for the 'tickets' table and create it if it does not exist
		createTable(db, "garages");
	
		//Load mock data into 'tickets' table if it is empty
		if(isTableEmpty("garages"))
		{
			GrgXMLParser grgParse = new GrgXMLParser();
			garages = grgParse.runExample("http://www.internfair.internshala.com/internFiles/AppDesign/GarageList.xml");
			
			for(int i=0; i<garages.size(); i++)
			{
				temp = garages.get(i);
				geo_street = temp.getAddr().getStreet().replace(" ", "+");
				geo_city = temp.getAddr().getCity().replace(" ", "+");
				geo_state = temp.getAddr().getState().replace(" ", "+");
				
				geo_url = geo_street + ",+" + geo_city + ",+" + geo_state + ", India";
				JSONObject json = UpdateListActivity.getLocationInfo(geo_url);
				latlng = UpdateListActivity.getLatLong(json);
				temp.setLat(latlng.getLatitudeE6()/1E6);
				temp.setLng(latlng.getLongitudeE6()/1E6);
				
				insert(temp);
			}
		}
	}
	
	private static void createTable(SQLiteDatabase database, String tableName)
	{
		try
		{
			//begin the transaction
			database.beginTransaction();
			
			// Create a table
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT," 
					+ "name TEXT,"
					+ "cashless TEXT," 
					+ "manuf TEXT," 
					+ "street TEXT,"
					+ "city TEXT,"
					+ "pincode TEXT,"
					+ "state TEXT,"
					+ "person TEXT,"
					+ "landline TEXT,"
					+ "mobile TEXT,"
					+ "email TEXT,"
					+ "lat REAL,"
					+ "lng REAL"
					+ ");";
			database.execSQL(tableSql);
			
			//this makes sure transaction is committed
			database.setTransactionSuccessful();
		} 
		finally
		{
			database.endTransaction();
		}
	}
	
	private static boolean isTableEmpty(String table)
	{
		Cursor cursor = null;
		try
		{
			cursor = db.rawQuery("SELECT count(*) FROM "+table, null);
			
			int countIndex = cursor.getColumnIndex("count(*)");
			cursor.moveToFirst();
			int rowCount = cursor.getInt(countIndex);
			if(rowCount > 0)
			{
				return false;
			}
			
			return true;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	public static void insert(Garage g)
	{
		try
		{
			db.beginTransaction();
			
			//insert this row
			String name = g.getName();
			String cashless = g.getCashless();
			String manuf = g.getManufacturer();
			String street = g.getAddr().getStreet();
			String city = g.getAddr().getCity();
			String pincode = g.getAddr().getPincode();
			String state = g.getAddr().getState();
			String person = g.getConDetails().getPerson();
			String landline = g.getConDetails().getLandline();
			String mobile = g.getConDetails().getMobile();
			String email = g.getConDetails().getEmail();
			double lat = g.getLat();
			double lng = g.getLng();
			
			String insert = "INSERT INTO garages "+" (name,cashless,manuf,street,city,pincode,state,person,landline,mobile,email,lat,lng) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
			
			db.execSQL(insert,new Object[]{name,cashless,manuf,street,city,pincode,state,person,landline,mobile,email,lat,lng});
			
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}
	
	public static ArrayList<Garage> readAll(int type, String param)
	{
		Cursor cursor = null;
		try
		{
			ArrayList<Garage> all = new ArrayList<Garage>();
			
			switch(type)
			{
			case 1: cursor = db.rawQuery("SELECT * FROM garages", null);
					break;
			case 2: cursor = db.rawQuery("SELECT * FROM garages WHERE cashless='Yes'", null);
					break;
			case 3: cursor = db.rawQuery("SELECT * FROM garages WHERE manuf='"+ param +"'", null);
					break;
			case 4: cursor = db.rawQuery("SELECT * FROM garages WHERE city='"+ param +"'", null);
					break;
			}
			
			
			if(cursor.getCount() > 0)
			{
				int idIndex = cursor.getColumnIndex("id");
				int nameIndex = cursor.getColumnIndex("name");
				int cashlessIndex = cursor.getColumnIndex("cashless");
				int manufIndex = cursor.getColumnIndex("manuf");
				int streetIndex = cursor.getColumnIndex("street");
				int cityIndex = cursor.getColumnIndex("city");
				int pincodeIndex = cursor.getColumnIndex("pincode");
				int stateIndex = cursor.getColumnIndex("state");
				int personIndex = cursor.getColumnIndex("person");
				int landlineIndex = cursor.getColumnIndex("landline");
				int mobileIndex = cursor.getColumnIndex("mobile");
				int emailIndex = cursor.getColumnIndex("email");
				int latIndex = cursor.getColumnIndex("lat");
				int lngIndex = cursor.getColumnIndex("lng");
				
				cursor.moveToFirst();
				do
				{
					int id = cursor.getInt(idIndex);
					String name = cursor.getString(nameIndex);
					String cashless = cursor.getString(cashlessIndex);
					String manuf = cursor.getString(manufIndex);
					String street = cursor.getString(streetIndex);
					String city = cursor.getString(cityIndex);
					String pincode = cursor.getString(pincodeIndex);
					String state = cursor.getString(stateIndex);
					String person = cursor.getString(personIndex);
					String landline = cursor.getString(landlineIndex);
					String mobile = cursor.getString(mobileIndex);
					String email = cursor.getString(emailIndex);
					double lat = cursor.getFloat(latIndex);
					double lng = cursor.getFloat(lngIndex);
					
					Garage g = new Garage();
					Address_ a = new Address_();
					ContactDetails cd = new ContactDetails();
					
					a.setCity(city);
					a.setPincode(pincode);
					a.setState(state);
					a.setStreet(street);
					
					cd.setEmail(email);
					cd.setLandline(landline);
					cd.setMobile(mobile);
					cd.setPerson(person);
					
					g.setName(name);
					g.setCashless(cashless);
					g.setManufacturer(manuf);
					g.setAddr(a);
					g.setConDetails(cd);
					g.setLat(lat);
					g.setLng(lng);
					all.add(g);
					
					cursor.moveToNext();
				}while(!cursor.isAfterLast());
			}
			
			return all;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	public static ArrayList<String> getAllCities()
	{
		Cursor cursor = null;
		try
		{
			ArrayList<String> allCities = new ArrayList<String>();
			cursor = db.rawQuery("SELECT DISTINCT city FROM garages", null);
			
			
			if(cursor.getCount() > 0)
			{
				int cityIndex = cursor.getColumnIndex("city");
				
				cursor.moveToFirst();
				do
				{
					String city = cursor.getString(cityIndex);
					allCities.add(city);
					cursor.moveToNext();
				}while(!cursor.isAfterLast());
			}
			
			return allCities;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	public static ArrayList<String> getAllManuf()
	{
		Cursor cursor = null;
		try
		{
			ArrayList<String> allManuf = new ArrayList<String>();
			cursor = db.rawQuery("SELECT DISTINCT manuf FROM garages", null);
			
			
			if(cursor.getCount() > 0)
			{
				int manufIndex = cursor.getColumnIndex("manuf");
				
				cursor.moveToFirst();
				do
				{
					String manuf = cursor.getString(manufIndex);
					allManuf.add(manuf);
					cursor.moveToNext();
				}while(!cursor.isAfterLast());
			}
			
			return allManuf;
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
			}
		}
	}
	
	
	public static void drop(Context context)
	{
		try
		{
			db.beginTransaction();
			
			String delete = "DELETE FROM garages";
			//db.execSQL(delete);
			
			String drop = "DROP TABLE IF EXISTS berkshire.garages";
			db.execSQL(drop);
			
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
			context.deleteDatabase("berkshire.db");
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
