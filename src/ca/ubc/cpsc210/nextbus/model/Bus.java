package ca.ubc.cpsc210.nextbus.model;

import ca.ubc.cpsc210.nextbus.util.LatLon;

/**
 * Bus information, including route, position (lat/lon),
 * destination, time at which location was last updated.
 */
public class Bus {
	private BusRoute route;
	private String dest;
	private String time;
	private LatLon latlon;

	/**
	 * Constructor 
	 * @param route  the bus route
	 * @param lat    latitude of bus
	 * @param lon    longitude of bus
	 * @param dest   destination
	 * @param time   time at which location was recorded
	 */
	public Bus(BusRoute route, double lat, double lon, String dest, String time) {
		this.route = route;
		this.dest = dest;
		this.time = time;
		latlon = new LatLon(lat, lon);
	}

	/**
	 * Gets bus route
	 * @return bus route
	 */
	public BusRoute getRoute() {
		return route;
	}

	/**
	 * Gets bus location as LatLon object
	 * @return bus location 
	 */
	public LatLon getLatLon() {
		return latlon;
	}
	
	/**
	 * Gets destination
	 * @return destination of this bus
	 */
	public String getDestination() {
	    return dest;
	}
	
	/**
	 * Gets time bus location was recorded
	 * @return  time location was recorded
	 */
	public String getTime() {
	    return time;
	}

	/**
	 * Produce a string describing destination and time
	 * that location was captured in the form:
	 * 
	 * <p>Destination: &lt;dest&gt; <br>
	 * Location at: &lt;time&gt;
	 * 
	 * <p>For example:
	 * 
	 * <p>"Destination: UBC <br>
	 *  Location at: 10:23:42"
	 *  
	 * @return string describing destination and time location was captured
	 */
	public String getDescription() {
		return "Destination: " + dest + "\nLocation at: " + time;
	}
}
