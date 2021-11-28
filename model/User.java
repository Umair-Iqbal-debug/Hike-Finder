package model;

import java.io.Serializable;

//a. Fields:
//i. firstName (String)
//ii. lastName (String)
//iii. isAdmin (Boolean)
//iv. email (String)
//v. username (String, unique )
//vi. password (String, minimum 6 characters in length with at least one capital 
//letter, one lower case, and one digit)
//vii. picture (String, file name of a profile picture)
//viii. hikingHistory (HikingHistory, see below).
//b. Methods:
//i. Constructor, getter, setter, and toString methods


public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1561263541951737246L;
	private String firstName;
	private String lastName;
	private boolean isAdmin;
	private String email;
	private String username;
	private String adminStatus;
	private String password;
	private String picture;  // filename of a profile picture
	private HikingHistory hikingHistory;
	
	public User(String firstName, String lastName, boolean isAdmin, String email, String username, String password,
			String picture) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
		this.email = email;
		this.username = username;
		setPassword(password);
		this.picture = picture;
		this.hikingHistory = new HikingHistory();
		setAdminStatus((isAdmin) ? "Yes":"No");
	}
	
	
	public User(String firstName, String lastName,String email,String username,String password, String picture)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		setPassword(password);
		this.picture = picture;
		this.hikingHistory = new HikingHistory();
		setAdminStatus((isAdmin) ? "Yes":"No");
	}
	
	
	
	public User(String username, String password, boolean isAdmin)
	{
		this.username = username;
		this.isAdmin = isAdmin;
		setPassword(password);
		this.hikingHistory = new HikingHistory();
		setAdminStatus((isAdmin) ? "Yes":"No");
	}
	
	public User(String username)
	{
		this.username = username;
	}
	
	public HikingHistory getHikingHistory()
	{
		return hikingHistory;
	}
	
	public void addHike(Hike hike)
	{
		hikingHistory.add(hike);
	}

	
	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public boolean isAdmin() 
	{
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) 
	{
		this.isAdmin = isAdmin;
		setAdminStatus((isAdmin) ? "Yes":"No");
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		if(Password.isValidPassword(password))this.password = password;
	}
	
	public boolean minLength(String s, int min)
	{
		return s.length() >= min;
	}
	
	public String getPicture() 
	{
		return picture;
	}
	
	public void setPicture(String picture) 
	{
		this.picture = picture;
	}
	
	public String getAdminStatus() {
		return adminStatus;
	}


	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}


	@Override
	public String toString() 
	{
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", isAdmin=" + isAdmin + ", email=" + email
			+ ", username=" + username + "]";
	}


	
//	@Override 
//	public String toString()
//	{
//		return firstName;
//	}
	
}
