package com.ncs.model;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.BooksBean;
import com.ncs.bean.LikesBean;
import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.exception.ApplicationException;
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
		 RegisterPrintedBookModel bookModel = new RegisterPrintedBookModel();
		 RegisterPrintedBookBean bookBean = bookModel.findByPk(bean.getBookNo());
	        bean.setBookName(bookBean.getBookName());
	        bean.setBookNo(bookBean.getBookId());
		System.out.println(bean.getBookName());
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO likes VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getBookName());
			pstmt.setString(4, bean.getBookNo());
			pstmt.setInt(5, bean.getLike1());
			pstmt.setInt(6, bean.getLike2());
			pstmt.setInt(7, bean.getLike3());
			pstmt.setTimestamp(8, bean.getDate());
			pstmt.setInt(9, bean.getLike4());
			pstmt.setInt(10, bean.getLike5());
			pstmt.setInt(11, bean.getLike6());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
			PreparedStatement pstmt1 = conn
					.prepareStatement("UPDATE registerprintedbook SET RL_PART_I=? WHERE BOOK_ID=?");
			pstmt1.setBoolean(1, true);
			pstmt1.setString(2, bean.getBookNo());
			pstmt1.executeUpdate();
			conn.commit(); // End transaction
			pstmt1.close();
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
	
	public long addPartTwo(LikesBean bean) throws Exception {
		Connection conn = null;
		 RegisterPrintedBookModel bookModel = new RegisterPrintedBookModel();
		 RegisterPrintedBookBean bookBean = bookModel.findByPk(bean.getBookNo());
	        bean.setBookName(bookBean.getBookName());
	        bean.setBookNo(bookBean.getBookId());
		System.out.println(bean.getBookName());
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO likes VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getBookName());
			pstmt.setString(4, bean.getBookNo());
			pstmt.setInt(5, bean.getLike1());
			pstmt.setInt(6, bean.getLike2());
			pstmt.setInt(7, bean.getLike3());
			pstmt.setTimestamp(8, bean.getDate());
			pstmt.setInt(9, bean.getLike4());
			pstmt.setInt(10, bean.getLike5());
			pstmt.setInt(11, bean.getLike6());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
			PreparedStatement pstmt1 = conn
					.prepareStatement("UPDATE registerprintedbook SET RL_PART_II=? WHERE BOOK_ID=?");
			pstmt1.setBoolean(1,true);
			pstmt1.setString(2, bean.getBookNo());
			pstmt1.executeUpdate();
			conn.commit(); // End transaction
			pstmt1.close();
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
				bean.setLike1(rs.getInt(5));
				bean.setLike2(rs.getInt(6));
				bean.setLike3(rs.getInt(7));
				bean.setDate(rs.getTimestamp(8));
				bean.setLike4(rs.getInt(9));
				bean.setLike5(rs.getInt(10));
				bean.setLike6(rs.getInt(11));
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
	

	
	public List search(LikesBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	
	public List search(LikesBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM likes  WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail()
						+ "%'");
			}
			if (bean.getBookNo() != null && bean.getBookNo().length() > 0) {
				sql.append(" AND BOOK_NO like '" + bean.getBookNo()
						+ "%'");
			}
			if (bean.getBookName() != null && bean.getBookName().length() > 0) {
				sql.append(" AND BOOK_NAME like '" + bean.getBookName()
						+ "%'");
			}
		

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new LikesBean();
				bean.setId(rs.getInt(1));
				bean.setEmail(rs.getString(2));
				bean.setBookName(rs.getString(3));
				bean.setBookNo(rs.getString(4));
				bean.setLike1(rs.getInt(5));
				bean.setLike2(rs.getInt(6));
				bean.setLike3(rs.getInt(7));
				bean.setDate(rs.getTimestamp(8));
				bean.setLike4(rs.getInt(9));
				bean.setLike5(rs.getInt(10));
				bean.setLike6(rs.getInt(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
	e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in search Register");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	
		return list;
	}

}