package com.skillstorm.im.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.skillstorm.im.daos.CompanyDAO;
import com.skillstorm.im.daos.MySQLCompanyImp;
import com.skillstorm.im.models.Company;

public class CompanyDAOTest {
	
	CompanyDAO dao;
	
	public CompanyDAOTest() {
		this.dao = new MySQLCompanyImp();
	}
	
	// CompanyDAO Interface
	// public Company save(Company company);
	// public Company findById(int id);
	// public void update(Company company);
	// public void delete(Company company);
	// public void delete(int id);
	
	@Test
	public void CRUDCompany() {
		// Test save
		Company c = new Company("Test Company");
		Company saved = this.dao.save(c);
		assertNotNull("Company save return was null", saved);
		assertEquals("Company save return contained a different name", c.getName(), saved.getName());
		// Test find
		Company cFind = this.dao.findById(saved.getId());
		assertNotNull("Company find return was null", cFind);
		assertEquals("Company find return contained a different id", cFind.getId(), saved.getId());
		assertEquals("Company find return contained a different name", cFind.getName(), c.getName());
		// Test update
		String newName = "Test Company New Name";
		Company cUpdate = new Company(saved.getId(), newName);
		this.dao.update(cUpdate);
		cFind = this.dao.findById(saved.getId());
		assertNotNull("Company find return was null after update", cFind);
		assertNotEquals("Company update did not update name", cUpdate.getName(), saved.getName());
		assertEquals("Company updated with incorrect name", cUpdate.getName(), newName);
		// Test delete
		this.dao.delete(saved);
		cFind = this.dao.findById(saved.getId());
		assertNull("Company was still found after delete", cFind);
	}
}
