package com.skillstorm.im.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.im.conf.InventoryDBCredentials;
import com.skillstorm.im.models.Warehouse;

public class MySQLWarehouseImp implements WarehouseDAO {

	@Override
	public Warehouse save(Warehouse warehouse) {
		String sql = "INSERT INTO im_warehouse (im_ware_name, im_ware_desc, im_ware_comp) VALUES (?, ?, ?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, warehouse.getName());
			ps.setString(2, warehouse.getDescription());
			ps.setInt(3, warehouse.getParentId());
			
			// Check that data was saved
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				// Get the generated ID
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					warehouse.setId(key);
				}
				conn.commit();
				return warehouse;
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Warehouse> findByCompanyId(int id) {
		String sql = "SELECT * FROM im_company WHERE im_comp_id = (?)";
		
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			LinkedList<Warehouse> warehouses = new LinkedList<>();
			
			while(rs.next()) {
				Warehouse warehouse = new Warehouse(
						rs.getInt("im_ware_id"),
						rs.getString("im_ware_name"),
						rs.getString("im_ware_desc"),
						rs.getInt("im_ware_comp"));
				warehouses.add(warehouse);
			}
			return warehouses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Warehouse findById(int id) {
		String sql = "SELECT * FROM im_warehouse WHERE im_ware_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Warehouse warehouse) {
		String sql = "UPDATE im_warehouse SET im_ware_name = (?), im_ware_desc = (?) WHERE im_ware_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, warehouse.getName());
			ps.setString(2, warehouse.getDescription());
			ps.setInt(3, warehouse.getId());
			
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
	public void delete(Warehouse warehouse) {
		this.delete(warehouse.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM im_warehouse WHERE im_ware_id = (?)";
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
