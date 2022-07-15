package com.skillstorm.im.models;

public class Section extends InventoryEntity {
	
	private int capacity;

	public Section() {

	}

	public Section(String name, String desc, int capacity, int parent) {
		super(name, desc, parent);
		this.capacity = capacity;
	}

	public Section(int id, String name, String desc, int capacity, int parent) {
		super(id, name, desc, parent);
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
