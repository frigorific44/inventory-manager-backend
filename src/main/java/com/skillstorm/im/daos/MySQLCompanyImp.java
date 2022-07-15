package com.skillstorm.im.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.skillstorm.im.conf.InventoryDBCredentials;
import com.skillstorm.im.models.Company;

public class MySQLCompanyImp implements CompanyDAO {

	@Override
	public Company save(Company company) {
		String sql = "INSERT INTO im_company (im_comp_name) VALUES (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, company.getName());
			
			// Check that data was saved
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				// Get the generated ID
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					company.setId(key);
				}
				conn.commit();
				return company;
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Company findById(int id) {
		String sql = "SELECT * FROM im_company WHERE im_comp_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Company(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Company company) {
		String sql = "UPDATE im_company SET im_comp_name = (?) WHERE im_comp_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, company.getName());
			ps.setInt(2, company.getId());
			
			// Check that data was saved
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Company company) {
		this.delete(company.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM im_company WHERE im_comp_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			// Check that data was deleted
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
