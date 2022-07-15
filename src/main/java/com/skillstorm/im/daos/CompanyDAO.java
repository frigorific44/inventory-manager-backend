package com.skillstorm.im.daos;

import com.skillstorm.im.models.Company;

public interface CompanyDAO {
	public Company save(Company company);
	public Company findById(int id);
	public void update(Company company);
	public void delete(Company company);
	public void delete(int id);
}
