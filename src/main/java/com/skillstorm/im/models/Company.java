package com.skillstorm.im.models;

/**
 * Our top-level entity. A company corresponds one-to-one with a user,
 * and allows access to all the storage units under its purview.
 */
public class Company extends InventoryRoot {

	public Company() {
		
	}
	
	public Company(String name) {
		super(name);
	}
	
	public Company(int id, String name) {
		super(id, name);
	}

}
