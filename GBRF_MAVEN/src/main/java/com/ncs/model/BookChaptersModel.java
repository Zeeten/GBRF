package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.BookChaptersBean;
import com.ncs.exception.ApplicationException;
import com.ncs.util.JDBCDataSource;

public class BookChaptersModel {

	public List search(BookChaptersBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	
	public List search(BookChaptersBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM book_chapters  WHERE 1=1");

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
				bean = new BookChaptersBean();
				bean.setId(rs.getInt(1));
				bean.setBookId(rs.getLong(2));
				bean.setBookName(rs.getString(3));
                bean.setChapter1(rs.getString(4));
                bean.setChapter2(rs.getString(5));
                bean.setChapter3(rs.getString(6));
                bean.setChapter4(rs.getString(7));
                bean.setChapter5(rs.getString(8));
                bean.setChapter6(rs.getString(9));
                bean.setChapter7(rs.getString(10));
                bean.setChapter8(rs.getString(11));
                bean.setChapter9(rs.getString(12));
                bean.setChapter10(rs.getString(13));
                bean.setChapter11(rs.getString(14));
                bean.setChapter12(rs.getString(15));
                bean.setChapter13(rs.getString(16));
                bean.setChapter14(rs.getString(17));
                bean.setChapter15(rs.getString(18));
                bean.setChapter16(rs.getString(19));
                bean.setChapter17(rs.getString(20));
                bean.setChapter18(rs.getString(21));
                bean.setChapter19(rs.getString(22));
                bean.setChapter20(rs.getString(23));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
	e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in search Read and like award");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	
		return list;
	}


}
