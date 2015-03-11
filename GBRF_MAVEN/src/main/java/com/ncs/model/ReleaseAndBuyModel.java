package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ncs.bean.ReleaseAndBuyBean;
import com.ncs.util.JDBCDataSource;

public class ReleaseAndBuyModel {

	public List list() throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM release_buy");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ReleaseAndBuyBean bean = new ReleaseAndBuyBean();
				bean.setId(rs.getLong(1));
				bean.setTop10(rs.getInt(2));
				bean.setAmount(rs.getInt(3));
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
