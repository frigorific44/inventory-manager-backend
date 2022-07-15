package com.skillstorm.im.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.skillstorm.im.daos.CompanyDAO;
import com.skillstorm.im.daos.MySQLCompanyImp;
import com.skillstorm.im.daos.MySQLWarehouseImp;
import com.skillstorm.im.daos.WarehouseDAO;
import com.skillstorm.im.models.Company;
import com.skillstorm.im.models.Warehouse;

public class WarehouseDAOTest {
	
	CompanyDAO cDao;
	WarehouseDAO dao;
	
	public WarehouseDAOTest() {
		this.cDao = new MySQLCompanyImp();
		this.dao = new MySQLWarehouseImp();
	}
	
	//	public Warehouse save(Warehouse warehouse);
	//	public List<Warehouse> findByCompanyId(int id);
	//	public Warehouse findById(int id);
	//	public void update(Warehouse warehouse);
	//	public void delete(Warehouse warehouse);
	//	public void delete(int id);

	@Test
	public void CRUDWarehouse() {
		// Create parent company
		Company company = new Company("Test Company");
		company = this.cDao.save(company);
		
		// Test save
		Warehouse w = new Warehouse("Test Warehouse", "Test description", company.getId());
		Warehouse saved = this.dao.save(w);
		assertNotNull("Warehouse save return was null", saved);
		
		// Test find
		Warehouse wFind = this.dao.findById(saved.getId());
		assertNotNull("Warehouse find returned null", wFind);
		assertEquals("Warehouse saved incorrectly", saved.getName(), wFind.getName());
		assertEquals("Warehouse saved incorrectly", saved.getDescription(), wFind.getDescription());
		assertEquals("Warehouse saved incorrectly", saved.getParentId(), wFind.getParentId());
		
		// Test update
		Warehouse wUpdate = new Warehouse(saved.getId(), "Test Warehouse New", "Test description new", saved.getParentId());
		this.dao.update(wUpdate);
		wFind = this.dao.findById(saved.getId());
		assertNotNull("Warehouse was not found after update", wFind);
		assertNotEquals("Warehouse did not update", saved.getName(), wFind.getName());
		assertNotEquals("Warehouse did not update", saved.getDescription(), wFind.getDescription());
		
		// Test delete
//		this.dao.delete(saved);
//		cFind = this.dao.findById(saved.getId());
//		assertNull("Company was still found after delete", cFind);
		this.dao.delete(saved);
		wFind = this.dao.findById(saved.getId());
		assertNull("Warehouse was found", wFind);
		
		// Delete parent company
		this.cDao.delete(company);
	}

}
