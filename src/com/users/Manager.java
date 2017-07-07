package com.users;

public class Manager {
	
	private String managerID;
	private String fname;
	private String lname;
	
	/**
	 * @param managerID
	 * @param fname
	 * @param lname
	 */
	public Manager(String managerID, String fname, String lname) {
		super();
		this.managerID = managerID;
		this.fname = fname;
		this.lname = lname;
	}

	/**
	 * @return the managerID
	 */
	public String getManagerID() {
		return managerID;
	}

	/**
	 * @param managerID the managerID to set
	 */
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	
}
