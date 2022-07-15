package com.skillstorm.im.daos;

import java.util.List;

import com.skillstorm.im.models.Warehouse;

public interface WarehouseDAO {
	public Warehouse save(Warehouse warehouse);
	public List<Warehouse> findByCompanyId(int id);
	public Warehouse findById(int id);
	public void update(Warehouse warehouse);
	public void delete(Warehouse warehouse);
	public void delete(int id);
}
