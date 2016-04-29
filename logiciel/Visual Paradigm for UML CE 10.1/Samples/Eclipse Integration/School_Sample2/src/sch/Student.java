package sch;

import java.util.Date;

public class Student {
	
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	
	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String[] getNames() {
		return new String[] { this.firstName, this.lastName };
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	protected Date getDateOfBirth() {
		return this.dateOfBirth;
	}
}
