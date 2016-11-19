package com.twosnail.frame.commin.util.kit.file.xml.pojo;

import java.util.ArrayList;


/**
 * @note 
 */
public class Root {

	private ArrayList<Param> params = new ArrayList<Param>();
	
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	private ArrayList<Business> businesses = new ArrayList<Business>();

	/**
	 * @return the params
	 */
	public ArrayList<Param> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(ArrayList<Param> params) {
		this.params = params;
	}
	
	public void addParam(Param param){
		this.params.add(param);
	}

	/**
	 * @return the connections
	 */
	public ArrayList<Connection> getConnections() {
		return connections;
	}

	/**
	 * @param connections the connections to set
	 */
	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}
	
	public void addConnection(Connection connection){
		this.connections.add(connection);
	}

	/**
	 * @return the businesses
	 */
	public ArrayList<Business> getBusinesses() {
		return businesses;
	}

	/**
	 * @param businesses the businesses to set
	 */
	public void setBusinesses(ArrayList<Business> businesses) {
		this.businesses = businesses;
	}

	public void addBusiness(Business business){
		this.businesses.add(business);
	}
}
