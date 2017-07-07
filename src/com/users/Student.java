package com.users;

public class Student {

	private String fname;
	private String lname;
	private String[] coursesRegistered;
	private Integer status;
	private String statusDueDate;
	private String id;
	
	
	
	/**
	 * 
	 */
	public Student() {
		super();
	}

	/**
	 * 
	 * @param fname
	 * @param lname
	 * @param coursesRegistered
	 * @param status
	 * @param statusDueDate
	 * @param id
	 */
	public Student(String fname, String lname, String[] coursesRegistered, Integer status,
			String statusDueDate, String id) {
		this.fname = fname;
		this.lname = lname;
		this.coursesRegistered = coursesRegistered;
		this.status = status;
		this.statusDueDate = statusDueDate;
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String[] getCoursesRegistered() {
		return coursesRegistered;
	}
	public void setCoursesRegistered(String[] coursesRegistered) {
		this.coursesRegistered = coursesRegistered;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusDueDate() {
		return statusDueDate;
	}
	public void setStatusDueDate(String statusDueDate) {
		this.statusDueDate = statusDueDate;
	}
	
}
