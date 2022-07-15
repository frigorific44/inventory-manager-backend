package com.skillstorm.im.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.skillstorm.im.daos.CompanyDAO;
import com.skillstorm.im.daos.MySQLCompanyImp;
import com.skillstorm.im.daos.MySQLSectionImp;
import com.skillstorm.im.daos.MySQLWarehouseImp;
import com.skillstorm.im.daos.SectionDAO;
import com.skillstorm.im.daos.WarehouseDAO;
import com.skillstorm.im.models.Company;
import com.skillstorm.im.models.Section;
import com.skillstorm.im.models.Warehouse;

public class SectionDAOTest {
	
	CompanyDAO cDao;
	WarehouseDAO wDao;
	SectionDAO dao;
	
	public SectionDAOTest() {
		this.cDao = new MySQLCompanyImp();
		this.wDao = new MySQLWarehouseImp();
		this.dao = new MySQLSectionImp();
	}
	
	Company company;
	Warehouse warehouse;
	Section saved;
	
	@Before
	public void beforeEach() {
		// Create parent company, parent warehouse
		Company c = new Company("Test Company");
		company = this.cDao.save(c);
		Warehouse w = new Warehouse("Test Warehouse", "Test description", company.getId());
		warehouse = this.wDao.save(w);
		Section s = new Section("Test Section", "Test description", 10, warehouse.getId());
		saved = this.dao.save(s);
	}
	
	@After
	public void afterEach() {
		this.cDao.delete(company);
	}

	@Test
	public void CRUDSection() {
		// Test save
		assertNotNull("Section save return was null", saved);
		
		// Test find
		Section sFind = this.dao.findById(saved.getId());
		assertNotNull("Section find returned null", sFind);
		assertEquals("Section saved incorrectly", saved.getName(), sFind.getName());
		assertEquals("Section saved incorrectly", saved.getDescription(), sFind.getDescription());
		assertEquals("Section saved incorrectly", saved.getCapacity(), sFind.getCapacity());
		assertEquals("Section saved incorrectly", saved.getParentId(), sFind.getParentId());
		
		// Test update
		Section sUpdate = new Section(saved.getId(), "Test Section New", "Test description new", 0, saved.getParentId());
		this.dao.update(sUpdate);
		sFind = this.dao.findById(saved.getId());
		assertNotNull("Section was not found after update", sFind);
		assertNotEquals("Section did not update", saved.getName(), sFind.getName());
		assertNotEquals("Section did not update", saved.getDescription(), sFind.getDescription());
		assertNotEquals("Section did not update", saved.getCapacity(), sFind.getCapacity());
		
		// Test delete
		this.dao.delete(saved);
		sFind = this.dao.findById(saved.getId());
		assertNull("Section was found", sFind);
		
		// Delete parent warehouse, company
		this.wDao.delete(warehouse);
	}
	
	@Test
	public void cascadeDeleteFromWarehouse() {
		// Break-down
		this.wDao.delete(warehouse);
		Section sFind = this.dao.findById(saved.getId());
		assertNull("Section was found", sFind);
	}
	
	@Test
	public void findByCompany() {
		List<Section> sections = dao.findByWarehouseId(warehouse.getId());
		assertEquals("Wrong number of warehouses", 1, sections.size());
		Section sFind = sections.get(0);
		assertNotNull("Section find returned null", sFind);
		assertEquals("Section saved incorrectly", saved.getName(), sFind.getName());
		assertEquals("Section saved incorrectly", saved.getDescription(), sFind.getDescription());
		assertEquals("Section saved incorrectly", saved.getCapacity(), sFind.getCapacity());
		assertEquals("Section saved incorrectly", saved.getParentId(), sFind.getParentId());
	}

}
