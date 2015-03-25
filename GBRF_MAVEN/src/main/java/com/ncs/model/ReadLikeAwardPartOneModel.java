package com.ncs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.ncs.bean.ReadLikeAwardPartOneBean;
import com.ncs.exception.ApplicationException;
import com.ncs.util.JDBCDataSource;

public class ReadLikeAwardPartOneModel {
	

	public long addChapterOne() throws Exception {
		Connection conn = null;
		long pk = 0;
	
		try {
			conn = JDBCDataSource.getConnection();
			//conn =	DriverManager.getConnection( "jdbc:mysql://localhost:3306/gbrf_test","root","");
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM best_first_chapter_user_list");
			pstmt1.executeUpdate();
			conn.commit(); // End transaction
			pstmt1.close();
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO best_first_chapter_user_list(FIRST_NAME,LAST_NAME,EMAIL,BOOK_NO,DATE) VALUES(?,?,?,?,?)");
			java.sql.Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery("SELECT customer.firstname AS firstname, customer.lastname AS lastname,"
 + " gbrf_test.likes.EMAIL AS EMAIL, gbrf_test.likes.BOOK_NO AS BOOK_NO, gbrf_test.likes.DATE    AS date "+
"FROM (`gbrf_test`.`likes` JOIN `kip_test`.`oc_customer` `customer` )"+
"WHERE ((`gbrf_test`.`likes`.`LIKE1` = (SELECT  `gbrf_test`.`likes`.`LIKE1`    AS `like1`  FROM `gbrf_test`.`likes` GROUP BY `gbrf_test`.`likes`.`LIKE1`"+
                                      " ORDER BY COUNT(*)DESC LIMIT 1)) AND ((`gbrf_test`.`likes`.`EMAIL` ) = `customer`.`email`)) GROUP BY `gbrf_test`.`likes`.`EMAIL` LIMIT 100"); 
			while ( rs.next() )  
			{  
			String firstname = rs.getString(1);
			String lastname = rs.getString(2);
			String EMAIL = rs.getString(3);
			String BOOK_NO = rs.getString(4);
			Timestamp date=rs.getTimestamp(5);
			pstmt.setString(1, firstname);
			pstmt.setString(2, lastname);          
			pstmt.setString(3, EMAIL); 
			pstmt.setString(4, BOOK_NO); 
			pstmt.setTimestamp(5,date);
			pstmt.executeUpdate();
			}
	
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
	
	public long addChapterSecond() throws Exception {
		Connection conn = null;
		long pk = 0;
	
		try {
			conn = JDBCDataSource.getConnection();
			//conn =	DriverManager.getConnection( "jdbc:mysql://localhost:3306/gbrf_test","root","");
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM best_second_chapter_user_list");
			pstmt1.executeUpdate();
			conn.commit(); // End transaction
			pstmt1.close();
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO best_second_chapter_user_list(FIRST_NAME,LAST_NAME,EMAIL,BOOK_NO,DATE) VALUES(?,?,?,?,?)");
			java.sql.Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery("SELECT customer.firstname AS firstname, customer.lastname AS lastname,"
 + " gbrf_test.likes.EMAIL AS EMAIL, gbrf_test.likes.BOOK_NO AS BOOK_NO, gbrf_test.likes.DATE    AS date "+
"FROM (`gbrf_test`.`likes` JOIN `kip_test`.`oc_customer` `customer` )"+
"WHERE ((`gbrf_test`.`likes`.`LIKE2` = (SELECT  `gbrf_test`.`likes`.`LIKE2`    AS `like2`  FROM `gbrf_test`.`likes` GROUP BY `gbrf_test`.`likes`.`LIKE2`"+
                                      " ORDER BY COUNT(*)DESC LIMIT 1))" +
                                 "  AND NOT(`gbrf_test`.`likes`.`EMAIL` IN(SELECT `award_like1`.`EMAIL` AS `email`  FROM `gbrf_test`.`best_first_chapter_user_list` `award_like1`)) "+ 
                                              		  
					" AND ((`gbrf_test`.`likes`.`EMAIL` ) = `customer`.`email`)) GROUP BY `gbrf_test`.`likes`.`EMAIL` LIMIT 100"); 
			while ( rs.next() )  
			{  
			String firstname = rs.getString(1);
			String lastname = rs.getString(2);
			String EMAIL = rs.getString(3);
			String BOOK_NO = rs.getString(4);
			Timestamp date=rs.getTimestamp(5);
			pstmt.setString(1, firstname);
			pstmt.setString(2, lastname);          
			pstmt.setString(3, EMAIL); 
			pstmt.setString(4, BOOK_NO); 
			pstmt.setTimestamp(5,date);
			pstmt.executeUpdate();
			}
	
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
	
	public long addChapterThird() throws Exception {
		Connection conn = null;
		long pk = 0;
	
		try {
			conn = JDBCDataSource.getConnection();
			//conn =	DriverManager.getConnection( "jdbc:mysql://localhost:3306/gbrf_test","root","");
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM best_third_chapter_user_list");
			pstmt1.executeUpdate();
			conn.commit(); // End transaction
			pstmt1.close();
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO best_third_chapter_user_list(FIRST_NAME,LAST_NAME,EMAIL,BOOK_NO,DATE) VALUES(?,?,?,?,?)");
			java.sql.Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery("SELECT customer.firstname AS firstname, customer.lastname AS lastname,"
 + " gbrf_test.likes.EMAIL AS EMAIL, gbrf_test.likes.BOOK_NO AS BOOK_NO, gbrf_test.likes.DATE    AS date "+
"FROM (`gbrf_test`.`likes` JOIN `kip_test`.`oc_customer` `customer` )"+
"WHERE ((`gbrf_test`.`likes`.`LIKE3` = (SELECT  `gbrf_test`.`likes`.`LIKE3`    AS `like3`  FROM `gbrf_test`.`likes` GROUP BY `gbrf_test`.`likes`.`LIKE3`"+
                                      " ORDER BY COUNT(*)DESC LIMIT 1))" +
                                 "  AND NOT(`gbrf_test`.`likes`.`EMAIL` IN(SELECT `award_like1`.`EMAIL` AS `email`  FROM `gbrf_test`.`best_first_chapter_user_list` `award_like1`)) "+ 
                                 "  AND NOT(`gbrf_test`.`likes`.`EMAIL` IN(SELECT `award_like2`.`EMAIL` AS `email`  FROM `gbrf_test`.`best_second_chapter_user_list` `award_like2`)) "+             		  
					" AND ((`gbrf_test`.`likes`.`EMAIL` ) = `customer`.`email`)) GROUP BY `gbrf_test`.`likes`.`EMAIL` LIMIT 100" ); 
			while ( rs.next() )  
			{  
			String firstname = rs.getString(1);
			String lastname = rs.getString(2);
			String EMAIL = rs.getString(3);
			String BOOK_NO = rs.getString(4);
			Timestamp date=rs.getTimestamp(5);
			pstmt.setString(1, firstname);
			pstmt.setString(2, lastname);          
			pstmt.setString(3, EMAIL); 
			pstmt.setString(4, BOOK_NO); 
			pstmt.setTimestamp(5,date);
			pstmt.executeUpdate();
			}
	
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

	public List searchChapterOne(ReadLikeAwardPartOneBean bean) throws ApplicationException {
		return searchChapterOne(bean, 0, 0);
	}
	
	public List searchChapterOne(ReadLikeAwardPartOneBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM best_first_chapter_user_list  WHERE 1=1");

		if (bean != null) {
		
		/*	if (bean.getBookName() != null && bean.getBookName().length() > 0) {
				sql.append(" AND BOOK_NAME like '" + bean.getBookName()
						+ "%'");
			}*/
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail()
						+ "%'");
			}
			if (bean.getBookNo() != null && bean.getBookNo().length() > 0) {
				sql.append(" AND BOOK_NO like '" + bean.getBookNo()
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
				bean = new ReadLikeAwardPartOneBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setBookNo(rs.getString(5));
				bean.setSubmitDate(rs.getTimestamp(6));
			/*	bean.setBookId(rs.getLong(2));
				bean.setBookName(rs.getString(4));
				bean.setAmount(rs.getInt(5));
				bean.setAwardDate(rs.getTimestamp(6));
			*/
			
			
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
	
	public List searchChapterSecond(ReadLikeAwardPartOneBean bean) throws ApplicationException {
		return searchChapterSecond(bean, 0, 0);
	}
	
	public List searchChapterSecond(ReadLikeAwardPartOneBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM best_second_chapter_user_list  WHERE 1=1");

		if (bean != null) {
		
		/*	if (bean.getBookName() != null && bean.getBookName().length() > 0) {
				sql.append(" AND BOOK_NAME like '" + bean.getBookName()
						+ "%'");
			}*/
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail()
						+ "%'");
			}
			if (bean.getBookNo() != null && bean.getBookNo().length() > 0) {
				sql.append(" AND BOOK_NO like '" + bean.getBookNo()
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
				bean = new ReadLikeAwardPartOneBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setBookNo(rs.getString(5));
				bean.setSubmitDate(rs.getTimestamp(6));
			/*	bean.setBookId(rs.getLong(2));
				bean.setBookName(rs.getString(4));
				bean.setAmount(rs.getInt(5));
				bean.setAwardDate(rs.getTimestamp(6));
			*/
			
			
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
	
	
	public List searchChapterThird(ReadLikeAwardPartOneBean bean) throws ApplicationException {
		return searchChapterThird(bean, 0, 0);
	}
	
	public List searchChapterThird(ReadLikeAwardPartOneBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM best_third_chapter_user_list  WHERE 1=1");

		if (bean != null) {
		
		/*	if (bean.getBookName() != null && bean.getBookName().length() > 0) {
				sql.append(" AND BOOK_NAME like '" + bean.getBookName()
						+ "%'");
			}*/
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail()
						+ "%'");
			}
			if (bean.getBookNo() != null && bean.getBookNo().length() > 0) {
				sql.append(" AND BOOK_NO like '" + bean.getBookNo()
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
				bean = new ReadLikeAwardPartOneBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setBookNo(rs.getString(5));
				bean.setSubmitDate(rs.getTimestamp(6));
			/*	bean.setBookId(rs.getLong(2));
				bean.setBookName(rs.getString(4));
				bean.setAmount(rs.getInt(5));
				bean.setAwardDate(rs.getTimestamp(6));
			*/
			
			
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
