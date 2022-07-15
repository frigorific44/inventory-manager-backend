package com.skillstorm.im.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
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
	
	Company company;
	Warehouse saved;
	
	@Before
	public void beforeEach() {
		// Create parent company
		Company c = new Company("Test Company");
		company = this.cDao.save(c);
		Warehouse w = new Warehouse("Test Warehouse", "Test description", company.getId());
		saved = this.dao.save(w);
	}

	@Test
	public void CRUDWarehouse() {
		// Test save
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
		this.dao.delete(saved);
		wFind = this.dao.findById(saved.getId());
		assertNull("Warehouse was found", wFind);
		
		// Delete parent company
		this.cDao.delete(company);
	}
	
	@Test
	public void cascadeDeleteFromCompany() {
		// Break-down
		this.cDao.delete(company);
		Warehouse wFind = this.dao.findById(saved.getId());
		
		assertNull("Warehouse was found", wFind);
	}
	
	@Test
	public void findByCompany() {
		List<Warehouse> warehouses = dao.findByCompanyId(company.getId());
		assertEquals("Wrong number of warehouses", 1, warehouses.size());
		Warehouse wFind = warehouses.get(0);
		assertNotNull("Warehouse find returned null", wFind);
		assertEquals("Warehouse saved incorrectly", saved.getName(), wFind.getName());
		assertEquals("Warehouse saved incorrectly", saved.getDescription(), wFind.getDescription());
		assertEquals("Warehouse saved incorrectly", saved.getParentId(), wFind.getParentId());
	}

}
