package com.adhithya.app.ws.ui.model.response;

/*
 * the file name has rest to specify this goes back to the ui
 * only those data that needs to be returned from the database
 * sensitive information should not be returned from this
 * 
 * java object to outgoing response
 * sent back as a confirmation after user is created */

public class UserRest {

	/*
	 * This user id is not the database user id, it is just a random alpha numeric
	 * number so that it cannot be used to guess the next user. It is a public user
	 * id
	 */
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String message;

	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
