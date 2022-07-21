package com.skillstorm.im.models;

/**
 * The largest storage unit. A warehouse contains sections, which contain
 * palletized items.
 */
public class Warehouse extends InventoryEntity {

	public Warehouse() {

	}
	
	public Warehouse(String name, String desc, int parent) {
		super(name, desc, parent);
	}
	
	public Warehouse(int id, String name, String desc, int parent) {
		super(id, name, desc, parent);
	}

}
