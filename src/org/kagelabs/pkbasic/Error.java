package org.kagelabs.pkbasic;

/**
 * Utility class for error storage
 * @author Patrick Kage
 *
 */
public class Error {
	private String name;
	private String reason;
	private String origin;
	
	public Error(String name, String reason, String origin) {
		this.name = name;
		this.reason = reason;
		this.origin = origin;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
}
