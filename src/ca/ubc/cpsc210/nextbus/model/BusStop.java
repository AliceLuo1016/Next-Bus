/*
 * Copyright 2014 Department of Computer Science, University of British Columbia,
 * unless otherwise noted herein. 
 * 
 * As a student in CPSC 210 you have permission to make copies of this material for
 * the purposes of completing the term project and submitting it for grading.  
 * 
 * You must preserve this copyright notice on all copies.
 * 
 * You must not redistribute this material or make it available to others, electronically
 * or otherwise, except to your registered partner in Phase 2 of the term project.  
 */

package ca.ubc.cpsc210.nextbus.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ubc.cpsc210.nextbus.util.LatLon;

/**
 * Bus stop information including stop number, description,
 * location (lat/lon), routes that service stop, estimated wait
 * times for buses at this stop and buses serving this stop.
 */
public class BusStop {
	private int stopNum;
	private String locationDescription;
	private LatLon latlon;
	private Set<BusRoute> routes;
	private Set<BusWaitTime> waitTimes;
	private List<Bus> buses;
	
	/**
	 * Constructor 
	 * 		Creates bus stop having stop number, name, lat/lon and routes
	 *      parsed from JSON object.  Wait times and bus locations are empty.
	 * 
	 * @param json  JSON object representing bus stop
	 * @throws JSONException  if bus stop cannot be parsed from JSON object
	 */
	public BusStop(JSONObject json) throws JSONException {
		stopNum = json.getInt("StopNo");
		locationDescription = json.getString("Name");
		latlon = getLatLonFromJSON(json);
		routes = getRoutesFromJSON(json);
		waitTimes = new TreeSet<BusWaitTime>();
		buses = new ArrayList<Bus>();
	}
	
	/**
	 * Constructor 
	 * 
	 * @param stopNum   the stop number
	 * @param location  description of bus stop location
	 * @param lat       latitude of stop
	 * @param lon       longitude of stop
	 * @param routes    set of routes that service this stop
	 */
	public BusStop(int stopNum, String location, double lat, double lon, Set<BusRoute> routes) {
		this.stopNum = stopNum;
		this.locationDescription = location;
		latlon = new LatLon(lat, lon);
		this.routes = routes;
		waitTimes = new TreeSet<BusWaitTime>();
		buses = new ArrayList<Bus>();
	}

	/**
	 * Gets stop number
	 * @return stop number
	 */
	public int getStopNum() {
		return stopNum;
	}

	/**
	 * Gets description of bus stop location
	 * @return  description of bus stop location
	 */
	public String getLocationDesc() {
		return locationDescription;
	}

	/**
	 * Gets location of bus stop as LatLon object
	 * @return  location of bus stop
	 */
	public LatLon getLatLon() {
		return latlon;
	}
	
	/**
	 * Gets set of wait times for buses at this stop sorted using natural ordering
	 * defined for BusWaitTime.  
	 * 
	 * @see ca.ubc.cpsc210.nextbus.model.BusWaitTime#compareTo(BusWaitTime)
	 * 
	 * @return  set of wait times for buses at this stop
	 */
	public Set<BusWaitTime> getWaitTimes() {
        return waitTimes;
    }
	
	/**
	 * Return bus route with specified name or null if no such route serves this stop.
	 * 
	 * @param routeName  route name
	 * @return bus route with given name or null if no such route serves this stop.
	 */
	public BusRoute getRouteNamed(String routeName) {
		for(BusRoute route : routes)
			if(route.getName().equals(routeName))
				return route;
		
		return null;
	}

	/**
	 * Add an estimated wait time for a bus at this stop
	 * @param bwt estimated wait time
	 */
	public void addWaitTime(BusWaitTime bwt) {
		waitTimes.add(bwt);
	}
	
	/**
	 * Clear wait times for buses at this stop
	 */
	public void clearWaitTimes() {
		waitTimes.clear();
	}
	
	/**
	 * Add a bus that is serving this stop
	 * @param b  bus  to add
	 */
	public void addBus(Bus b) {
		buses.add(b);
	}
	
	/**
	 * Clear list of buses serving this stop
	 */
	public void clearBuses() {
		buses.clear();
	}
	
    /**
     * Get a list of buses currently serving this stop
     * 
     * @return list of buses serving this stop
     */
    public List<Bus> getBuses() {
        return buses;
    }	
	
    /**
     * Produces stop number and description of bus stop location in
     * the form: 
     * 
     * <p>&lt;stopNum&gt; &lt;locationDescription&lt;
     * 
     * <p>For example:
     * 
     * <p>"59269 UBC Loop Bay 5"
     */
	@Override
	public String toString() {
		return stopNum + " " + locationDescription;
	}

	/**
	 * Generate hash code based on stop number.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stopNum;
		return result;
	}

	/**
	 * Test equality of bus stops based on stop number.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusStop other = (BusStop) obj;
		if (stopNum != other.stopNum)
			return false;
		return true;
	}
	
	/**
	 * Parse LatLon object from JSONObject
	 * @param json  the JSONObject
	 * @return  a LatLon object parsed from json
	 * @throws JSONException 
	 */
	private LatLon getLatLonFromJSON(JSONObject json) throws JSONException {
		JSONObject jsonLatLon = json.getJSONObject("LatLon");
		String lat = jsonLatLon.getString("Latitude");
		String lon = jsonLatLon.getString("Longitude");
		return new LatLon(lat, lon);
	}
	
	/**
	 * Get routes as a JSONArray preserving only the route name
	 * @return JSON array of route names
	 */
	private JSONArray getRoutesAsJSON() {
		JSONArray routeNames = new JSONArray();
		
		for(BusRoute next : routes) {
			routeNames.put(next.getName());
		}
 		
		return routeNames;
	}
	
	/**
	 * Parse set of bus routes from JSONObject containing route names.  
	 * @param json  route names as JSONObject
	 * @return  set of bus routes having names parsed from json
	 * @throws JSONException
	 */
	private Set<BusRoute> getRoutesFromJSON(JSONObject json) throws JSONException {
		Set<BusRoute> routes = new HashSet<BusRoute>();
		JSONArray routeNames = json.getJSONArray("Routes");
		
		for(int index = 0; index < routeNames.length(); index++) {
			String routeName = routeNames.getString(index);
			BusRoute route = new BusRoute(routeName);
			routes.add(route);
		}
		
		return routes;
	}
	
	/**
	 * Produces JSONObject from this stop
	 * @return  the JSONObject containing this bus stop info
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("StopNo", stopNum);
		json.put("Name", locationDescription);
		json.put("LatLon", latlon.toJSON());
		json.put("Routes", getRoutesAsJSON());
		return json;
	}
}
