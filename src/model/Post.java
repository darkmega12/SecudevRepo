package model;

import java.util.Date;

public class Post {
	private int postid;
	private String username;
	private String message;
	private String attachment;
	private Date dateCreated;
	private Date dateModified;
	
	public Post(int postid, String username, String text, String attachment, Date dateCreated, Date dateModified) 
	{
		this.postid = postid;
		this.username = username;
		this.message = text;
		this.attachment = attachment;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public int getPostid() {
		return postid;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String text) {
		this.message = text;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
