package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.BooksBean;
import com.ncs.bean.LikesBean;
import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.util.JDBCDataSource;

public class RegisterPrintedBookModel {

	public long nextPK() throws Exception {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM registerprintedbook");
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
	public long add(RegisterPrintedBookBean bean) throws Exception {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO registerprintedbook VALUES(?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getBookName());
			pstmt.setInt(3, bean.getBookId());
			pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(5, bean.getMobileno());
			pstmt.setString(6, bean.getEmail());

			pstmt.setString(7, bean.getPassword());
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
	
	public List list(String email) throws Exception {
		System.out.println("iii"+email);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM registerprintedbook where EMAIL_ID=?");
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegisterPrintedBookBean bean = new RegisterPrintedBookBean();
				bean.setId(rs.getInt(1));
				bean.setBookName(rs.getString(2));
				bean.setBookId(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				bean.setMobileno(rs.getString(5));
				bean.setEmail(rs.getString(6));
	
				bean.setPassword(rs.getString(7));
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
	
	public List printedbooklist(String email) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM registerprintedbook where EMAIL_ID=? ");
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegisterPrintedBookBean bean = new RegisterPrintedBookBean();
				bean.setId(rs.getInt(1));
				bean.setBookName(rs.getString(2));
				bean.setBookId(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				bean.setMobileno(rs.getString(5));
				bean.setEmail(rs.getString(6));
	
				bean.setPassword(rs.getString(7));
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
