package com.adhithya.app.ws.ui.model.response;

import java.util.Date;

public class ResponseMessage {
	private Date timstamp;
	private String message;

	public ResponseMessage() {

	}

	public ResponseMessage(Date timstamp, String message) {
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
