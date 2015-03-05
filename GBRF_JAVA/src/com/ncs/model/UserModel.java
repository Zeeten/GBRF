package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ncs.bean.UserBean;
import com.ncs.util.JDBCDataSource;
import com.ncs.util.JDBCDataSource2;

public class UserModel {

	public long nextPK() throws Exception {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM user");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(UserBean bean) throws Exception {
		Connection conn = null;
		long pk = 0;
		if (findByLogin(bean.getEmail())) {
			throw new Exception("Email is already exist.");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getSurname());
			pstmt.setString(4, bean.getEmail());
			pstmt.setString(5, bean.getPassword());
			pstmt.setTimestamp(6, bean.getDate());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public boolean findByLogin(String email) throws Exception {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM user WHERE EMAIL=?");
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return flag;
	}

	public UserBean authenticate(String email, String password)
			throws Exception {
		Connection conn = null;
		UserBean bean = null;
		try {
			// SELECT customer_id,firstname,lastname,email,password FROM
			// oc_customer WHERE email=? AND password=?
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setSurname(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDate(rs.getTimestamp(6));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource2.closeConnection(conn);
		}
		return bean;
	}
}