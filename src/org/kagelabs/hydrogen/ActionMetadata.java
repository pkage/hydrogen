package org.kagelabs.hydrogen;

/**
 * Utility class to store metadata about an action
 * @author Patrick Kage
 *
 */
public class ActionMetadata {
	private String name; // invokable name
	private String arguments; // arg list in the form of "#$#"
	private String namespace;
	private char returnPrefix; // $, #, or \0
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	public char getReturnPrefix() {
		return returnPrefix;
	}
	public void setReturnPrefix(char returnPrefix) {
		this.returnPrefix = returnPrefix;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getFullName() {
		return (namespace.equals("")) ? name : namespace + "." + name;
	}
}
