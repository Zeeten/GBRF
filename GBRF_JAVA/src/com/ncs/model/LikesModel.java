package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.LikesBean;
import com.ncs.util.JDBCDataSource;

public class LikesModel {

	public long nextPK() throws Exception {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM likes");
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

	public long add(LikesBean bean) throws Exception {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO likes VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getBookName());
			pstmt.setString(4, bean.getBookNo());
			pstmt.setString(5, bean.getLike1());
			pstmt.setString(6, bean.getLike2());
			pstmt.setString(7, bean.getLike3());
			pstmt.setTimestamp(8, bean.getDate());
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
					.prepareStatement("SELECT * FROM likes");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				LikesBean bean = new LikesBean();
				bean.setId(rs.getInt(1));
				bean.setEmail(rs.getString(2));
				bean.setBookName(rs.getString(3));
				bean.setBookNo(rs.getString(4));
				bean.setLike1(rs.getString(5));
				bean.setLike2(rs.getString(6));
				bean.setLike3(rs.getString(7));
				bean.setDate(rs.getTimestamp(8));
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