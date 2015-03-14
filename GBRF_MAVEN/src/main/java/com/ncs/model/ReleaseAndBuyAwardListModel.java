package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.ReadLikeAwardPartOneBean;
import com.ncs.bean.ReleaseAndBuyAwardListBean;
import com.ncs.bean.ReleaseAndBuyBean;
import com.ncs.exception.ApplicationException;
import com.ncs.util.JDBCDataSource;

public class ReleaseAndBuyAwardListModel {

	public List search(ReleaseAndBuyAwardListBean bean)
			throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(ReleaseAndBuyAwardListBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM release_buy_award_list  WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getBookName() != null && bean.getBookName().length() > 0) {
				sql.append(" AND BOOK_NAME like '" + bean.getBookName() + "%'");
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
				bean = new ReleaseAndBuyAwardListBean();
				bean.setId(rs.getInt(1));
				bean.setBookId(rs.getLong(2));
				bean.setBookNo(rs.getString(3));
				bean.setBookName(rs.getString(4));
				bean.setAmount(rs.getInt(5));
				bean.setAwardDate(rs.getTimestamp(6));
				bean.setSubmitDate(rs.getTimestamp(7));
				bean.setFirstName(rs.getString(8));
				bean.setLastName(rs.getString(9));
				bean.setEmail(rs.getString(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in search Release and buy award list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
