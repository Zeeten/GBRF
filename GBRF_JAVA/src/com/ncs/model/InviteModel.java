package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ncs.bean.InviteBean;
import com.ncs.util.JDBCDataSource;

public class InviteModel {

	public long nextPK() throws Exception {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM invite");
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

	public long add(InviteBean bean) throws Exception {
		Connection conn = null;
		long pk = 0;
		if (findByLogin(bean.getEmail())) {
			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();
				// Get auto-generated next primary key
				conn.setAutoCommit(false); // Begin transaction
				PreparedStatement pstmt = conn
						.prepareStatement("INSERT INTO invite VALUES(?,?,?,?)");
				pstmt.setLong(1, pk);
				pstmt.setString(2, bean.getName());
				pstmt.setString(3, bean.getEmail());
				pstmt.setTimestamp(4, bean.getDate());
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
		}
		return pk;
	}

	public boolean findByLogin(String login) throws Exception {
		boolean flag = true;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM invite WHERE EMAIL=?");
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = false;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return flag;
	}
}