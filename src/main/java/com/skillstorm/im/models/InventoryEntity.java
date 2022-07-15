package com.skillstorm.im.models;

abstract class InventoryEntity extends InventoryRoot {

	String description;
	int parentId;
	
	public InventoryEntity() {
		
	}
	
	public InventoryEntity(String name, String desc, int parent) {
		super(name);
		this.description = desc;
		this.parentId = parent;
	}
	
	public InventoryEntity(int id, String name, String desc, int parent) {
		super(id, name);
		this.description = desc;
		this.parentId = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
