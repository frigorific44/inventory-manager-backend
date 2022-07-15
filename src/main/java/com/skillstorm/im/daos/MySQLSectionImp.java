package com.skillstorm.im.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.im.conf.InventoryDBCredentials;
import com.skillstorm.im.models.Section;

public class MySQLSectionImp implements SectionDAO {

	@Override
	public Section save(Section section) {
		String sql = "INSERT INTO im_section (im_sect_name, im_sect_desc, im_sect_cap, im_sect_ware) VALUES (?, ?, ?, ?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, section.getName());
			ps.setString(2, section.getDescription());
			ps.setInt(3, section.getCapacity());
			ps.setInt(4, section.getParentId());
			
			// Check that data was saved
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				// Get the generated ID
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					section.setId(key);
				}
				conn.commit();
				return section;
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Section> findByWarehouseId(int id) {
		String sql = "SELECT * FROM im_section WHERE im_sect_ware = (?)";
		
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			LinkedList<Section> sections = new LinkedList<>();
			
			while(rs.next()) {
				Section section = new Section(
						rs.getInt("im_sect_id"),
						rs.getString("im_sect_name"),
						rs.getString("im_sect_desc"),
						rs.getInt("im_sect_cap"),
						rs.getInt("im_sect_ware")
						);
				sections.add(section);
			}
			return sections;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Section findById(int id) {
		String sql = "SELECT * FROM im_section WHERE im_sect_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Section(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4),
						rs.getInt(5)
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Section section) {
		String sql = "UPDATE im_section SET im_sect_name = (?), im_sect_desc = (?), im_sect_cap = (?) WHERE im_sect_id = (?)";
		try (Connection conn = InventoryDBCredentials.getInstance().getConnection()) {
			// Start transaction
			conn.setAutoCommit(false);
			// Create prepared statement
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, section.getName());
			ps.setString(2, section.getDescription());
			ps.setInt(3, section.getCapacity());
			ps.setInt(4, section.getId());
			
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
	public void delete(Section section) {
		this.delete(section.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM im_section WHERE im_sect_id = (?)";
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
