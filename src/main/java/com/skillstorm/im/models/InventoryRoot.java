package com.skillstorm.im.models;

/**
 * All entities within the schema contain an id and a name.
 */
abstract class InventoryRoot {

	int id;
	String name;
	
	public InventoryRoot() {
		
	}
	
	public InventoryRoot(String name) {
		this.name = name;
	}
	
	public InventoryRoot(int id, String name) {
		this(name);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
