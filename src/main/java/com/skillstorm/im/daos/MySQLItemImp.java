package com.skillstorm.im.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skillstorm.im.conf.InventoryDBCredentials;
import com.skillstorm.im.models.Item;

public class MySQLItemImp implements ItemDAO {

	@Override
	public Item save(Item item) {
		String sql = "INSERT INTO im_item (im_item_sect, im_item_index, im_item_name, im_item_alt, im_item_desc, im_item_count) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, item.getParentId());
			ps.setInt(2, item.getId());
			ps.setString(3, item.getName());
			ps.setString(4, item.getAlt());
			ps.setString(5, item.getDescription());
			ps.setInt(6, item.getCount());
			
			// Check that data was saved
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
				return item;
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Item> findBySectionId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Item findById(int sectionId, int index) {
		String sql = "SELECT im_item_index, im_item_name, im_item_alt, im_item_desc, im_item_count, im_item_sect FROM im_item WHERE im_item_sect = (?) AND im_item_index = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sectionId);
			ps.setInt(2, index);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Item(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6)
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Item item) {
		String sql = "UPDATE im_item SET im_item_index = (?), im_item_name = (?), im_item_alt = (?), im_item_desc = (?), im_item_count = (?) WHERE im_item_sect = (?) AND im_item_index = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, item.getId());
			ps.setString(2, item.getName());
			ps.setString(3, item.getAlt());
			ps.setString(4, item.getDescription());
			ps.setInt(5, item.getCount());
			ps.setInt(6, item.getParentId());
			ps.setInt(7, item.getId());
			
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
	public void delete(Item item) {
		this.delete(item.getParentId(), item.getId());
	}

	@Override
	public void delete(int sectionId, int index) {
		String sql = "DELETE FROM im_item WHERE im_item_sect = (?) AND im_item_index = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sectionId);
			ps.setInt(2, index);
			
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
