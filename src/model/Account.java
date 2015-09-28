package model;

import java.util.Date;

public class Account {
	private String username;
	private String password;
	private String fName;
	private String lName;
	private String gender;
	private String salutation;
	private Date birthday;
	private String aboutme;
	private boolean isAdmin;
	private Date dateJoined;
	
	public Account(String username, String password, String firstname, String lastname, String gender, String salutation, Date birthday, boolean isAdmin, String aboutme, Date dateJoined){
		setUsername(username);
		setPassword(password);
		setfName(firstname);
		setlName(lastname);
		setGender(gender);
		setSalutation(salutation);
		setBirthday(birthday);
		setIsAdmin(isAdmin);
		setAboutme(aboutme);
		setDateJoined(dateJoined);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getAboutme() {
		return aboutme;
	}

	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}
}
