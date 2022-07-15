package com.skillstorm.im.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.skillstorm.im.daos.CompanyDAO;
import com.skillstorm.im.daos.ItemDAO;
import com.skillstorm.im.daos.MySQLCompanyImp;
import com.skillstorm.im.daos.MySQLItemImp;
import com.skillstorm.im.daos.MySQLSectionImp;
import com.skillstorm.im.daos.MySQLWarehouseImp;
import com.skillstorm.im.daos.SectionDAO;
import com.skillstorm.im.daos.WarehouseDAO;
import com.skillstorm.im.models.Company;
import com.skillstorm.im.models.Item;
import com.skillstorm.im.models.Section;
import com.skillstorm.im.models.Warehouse;

public class ItemDAOTest {

	CompanyDAO cDao;
	WarehouseDAO wDao;
	SectionDAO sDao;
	ItemDAO dao;
	
	public ItemDAOTest() {
		this.cDao = new MySQLCompanyImp();
		this.wDao = new MySQLWarehouseImp();
		this.sDao = new MySQLSectionImp();
		this.dao = new MySQLItemImp();
	}
	
	Company company;
	Warehouse warehouse;
	Section section;
	Item saved;
	
	@Before
	public void beforeEach() {
		// Create parent company, parent warehouse
		Company c = new Company("Test Company");
		company = this.cDao.save(c);
		Warehouse w = new Warehouse("Test Warehouse", "Test description", company.getId());
		warehouse = this.wDao.save(w);
		Section s = new Section("Test Section", "Test description", 10, warehouse.getId());
		section = this.sDao.save(s);
		Item i = new Item(2, "Test item", "Test alternate", "Test description", 1000, section.getId());
		saved = this.dao.save(i);
	}
	
	@After
	public void afterEach() {
		this.wDao.delete(warehouse);
		this.cDao.delete(company);
	}

	@Test
	public void CRUDItem() {
		// Test save
		assertNotNull("Section save returned null", saved);
		
		// Test find
		Item iFind = this.dao.findById(saved.getParentId(), saved.getId());
		assertNotNull("Item find returned null", iFind);
		assertEquals("Item saved incorrectly", saved.getName(), iFind.getName());
		assertEquals("Item saved incorrectly", saved.getAlt(), iFind.getAlt());
		assertEquals("Item saved incorrectly", saved.getDescription(), iFind.getDescription());
		assertEquals("Item saved incorrectly", saved.getCount(), iFind.getCount());
		
		// Test update
		Item iUpdate = new Item(saved.getId(), "Test Item New", "Test alt new", "Test description new", 1, saved.getParentId());
		this.dao.update(iUpdate);
		iFind = this.dao.findById(saved.getParentId(), iUpdate.getId());
		assertNotNull("Item was not found after update", iFind);
		assertNotEquals("Item did not update", saved.getName(), iFind.getName());
		assertNotEquals("Item did not update", saved.getAlt(), iFind.getAlt());
		assertNotEquals("Item did not update", saved.getDescription(), iFind.getDescription());
		assertNotEquals("Item did not update", saved.getCount(), iFind.getCount());
		
		// Test delete
		this.dao.delete(saved);
		iFind = this.dao.findById(saved.getParentId(), saved.getId());
		assertNull("section was found", iFind);
		
		// Delete parent section, warehouse, company
		this.sDao.delete(section);
	}
	
	@Test
	public void cascadeDeleteFromSection() {
		// Break-down
		this.sDao.delete(section);
		Item iFind = this.dao.findById(saved.getId(), 0);
		assertNull("Item was found", iFind);
	}
	
	@Test
	public void findByCompany() {
		List<Item> items = dao.findBySectionId(section.getId());
		assertEquals("Wrong number of warehouses", 1, items.size());
		Item iFind = items.get(0);
		assertEquals("Item saved incorrectly", saved.getName(), iFind.getName());
		assertEquals("Item saved incorrectly", saved.getAlt(), iFind.getAlt());
		assertEquals("Item saved incorrectly", saved.getDescription(), iFind.getDescription());
		assertEquals("Item saved incorrectly", saved.getCount(), iFind.getCount());
	}

}
