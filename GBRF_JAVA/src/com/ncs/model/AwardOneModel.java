package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.AwardOneBean;
import com.ncs.util.JDBCDataSource;
import com.ncs.util.JDBCDataSource2;

public class AwardOneModel {

	public List list() throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource2.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT `order_id`,`customer_id`,`firstname`,`lastname`,`email`,`telephone`,`payment_firstname`,`payment_lastname`,`payment_method`,`payment_code`,`order_status_id`,`total`,`currency_id`,`currency_code`,`currency_value`,`date_added` FROM oc_order WHERE order_status_id = 5 ORDER BY date_added LIMIT 0,10");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				AwardOneBean bean = new AwardOneBean();
				bean.setId(rs.getInt(1));
				bean.setCustomerId(rs.getInt(2));
				bean.setFirstName(rs.getString(3));
				bean.setLastName(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setTelephone(rs.getString(6));
				bean.setPaymentFirstName(rs.getString(7));
				bean.setPaymentLastName(rs.getString(8));
				bean.setPaymentMethod(rs.getString(9));
				bean.setPaymentCode(rs.getString(10));
				bean.setOrderStatusId(rs.getInt(11));
				bean.setTotal(rs.getDouble(12));
				bean.setCurrencyId(rs.getInt(13));
				bean.setCurrencyCode(rs.getString(14));
				bean.setCurrencyValue(rs.getDouble(15));
				bean.setDateAdded(rs.getTimestamp(16));
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
