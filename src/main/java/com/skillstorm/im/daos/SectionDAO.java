package com.skillstorm.im.daos;

import java.util.List;

import com.skillstorm.im.models.Section;

public interface SectionDAO {
	public Section save(Section section);
	public List<Section> findByWarehouseId(int id);
	public Section findById(int id);
	public void update(Section section);
	public void delete(Section section);
	public void delete(int id);
}
