package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.AwardOneBean;
import com.ncs.bean.GuestOfHonourBean;
import com.ncs.bean.LikesBean;
import com.ncs.bean.UserBean;
import com.ncs.exception.ApplicationException;
import com.ncs.util.JDBCDataSource;
import com.ncs.util.JDBCDataSource2;

public class GuestOfHonourModel {

	public long nextPK() throws Exception {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM GUEST_LIST");
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

	public long add(GuestOfHonourBean bean) throws Exception {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO GUEST_LIST VALUES(?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setTimestamp(3, bean.getDate());
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

	public List list() throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM GUEST_LIST");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				GuestOfHonourBean bean = new GuestOfHonourBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setDate(rs.getTimestamp(3));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}