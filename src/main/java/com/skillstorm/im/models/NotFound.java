package com.skillstorm.im.models;

/**
 * A container for request responses when an object is not found.
 */
public class NotFound {

	private String message;

	public NotFound() {

	}

	public NotFound(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
