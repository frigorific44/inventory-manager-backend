package com.skillstorm.im.models;

public class Item extends InventoryEntity {
//	Item is slightly different, in that its primary key is
//	made up of the section id, and its own id, which is
//	actually its index in the section. Hence, the difference
//	in parameter ordering (to stay consistent with the DB).
	private String alt;
	private int count;

	public Item() {

	}

//	An item will only be made with its id already chosen,
//	as its id is just the chosen index within the section.
	public Item(int id, String name, String alt, String desc, int count, int parent) {
		super(id, name, desc, parent);
		this.alt = alt;
		this.count = count;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
