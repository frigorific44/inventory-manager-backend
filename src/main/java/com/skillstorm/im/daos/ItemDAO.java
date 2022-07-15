package com.skillstorm.im.daos;

import java.util.List;

import com.skillstorm.im.models.Item;

public interface ItemDAO {
	public Item save(Item item);
	public List<Item> findBySectionId(int id);
	public Item findById(int sectionId, int index);
	public void update(Item item);
	public void delete(Item item);
	public void delete(int sectionId, int index);
}
