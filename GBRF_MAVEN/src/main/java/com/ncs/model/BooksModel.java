package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.BooksBean;
import com.ncs.bean.UserBean;
import com.ncs.exception.ApplicationException;
import com.ncs.util.JDBCDataSource;

public class BooksModel {

	public List list() throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM books");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BooksBean bean = new BooksBean();
				bean.setId(rs.getInt(1));
				bean.setBookName(rs.getString(2));
				bean.setNoOfChapters(rs.getInt(3));
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

	public List search(BooksBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	
	public List search(BooksBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM books  WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
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
				bean = new BooksBean();
				bean.setId(rs.getInt(1));
				bean.setBookName(rs.getString(2));
				bean.setNoOfChapters(rs.getInt(3));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
	e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in search Book");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	
		return list;
	}
}