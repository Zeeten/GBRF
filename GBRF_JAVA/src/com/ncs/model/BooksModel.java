package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.BooksBean;
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