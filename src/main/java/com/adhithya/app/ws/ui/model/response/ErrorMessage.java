package com.adhithya.app.ws.ui.model.response;

import java.util.Date;

public class ErrorMessage {
	private Date timstamp;
	private String message;

	public ErrorMessage() {

	}

	public ErrorMessage(Date timstamp, String message) {
		super();
		this.timstamp = timstamp;
		this.message = message;
	}

	public Date getTimstamp() {
		return timstamp;
	}

	public void setTimstamp(Date timstamp) {
		this.timstamp = timstamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
