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
	private int totCount;
	private int totDonate;
	private int totTrans;
	
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
		setTotCount(0);
		setTotDonate(0);
		setTotTrans(0);
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

	public String getTotCount() {
		if (totCount >= 3)
			return "Participant Badge";
		else if (totCount >= 5)
			return "Chatter Badge";
		else if (totCount >= 10)
			return "Socialite Badge";
		else
			return null;
	}

	public void setTotCount(int totCount) {
		this.totCount = totCount;
	}

	public String getTotDonate() {
		if (totDonate >= 5)
			return "Supporter Badge";
		else if (totDonate >= 20)
			return "Contributor Badge";
		else if (totDonate >= 100)
			return "Pillar Badge";
		else
			return null;
	}

	public void setTotDonate(int totDonate) {
		this.totDonate = totDonate;
	}

	public String getTotTrans() {
		if (totTrans >= 5)
			return "Shopper Badge";
		else if (totTrans >= 20)
			return "Promoter Badge";
		else if (totTrans >= 100)
			return "Elite Badge";
		else
			return null;
	}

	public void setTotTrans(int totTrans) {
		this.totTrans = totTrans;
	}

	public String getTotCollection() {
		if (totCount >= 3 && totDonate >= 5 && totTrans >= 5)
			return "Shopper Badge";
		else if (totCount >= 5 && totDonate >= 20 && totTrans >= 20)
			return "Promoter Badge";
		else if (totCount >= 10 && totDonate >= 100 && totTrans >= 100)
			return "Elite Badge";
		else
			return null;
	}
}
