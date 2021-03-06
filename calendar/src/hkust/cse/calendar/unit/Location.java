package hkust.cse.calendar.unit;

import java.io.Serializable;

/*
 * \class Location
 * \author JX 26-3-2015
 * a data type to for the Location information of an event.
 * available manipulations are
 * getLocationName and setLocationName 
 */
public class Location implements Serializable{
	private String locationName;
	
	//*conversion constructor 
	public Location(String name) {
		locationName = name;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String name) {
		locationName = name;
	}
}
