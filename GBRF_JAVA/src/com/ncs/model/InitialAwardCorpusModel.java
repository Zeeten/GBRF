package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ncs.bean.InitialAwardCorpusBean;
import com.ncs.util.JDBCDataSource;

public class InitialAwardCorpusModel {

	public List list() throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM initial_award_corpus");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				InitialAwardCorpusBean bean = new InitialAwardCorpusBean();
				bean.setId(rs.getLong(1));
				bean.setAmount(rs.getInt(2));
				bean.setNoOfAward(rs.getInt(3));
				bean.setTotalAmount(rs.getInt(4));
				bean.setPart(rs.getInt(5));
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
