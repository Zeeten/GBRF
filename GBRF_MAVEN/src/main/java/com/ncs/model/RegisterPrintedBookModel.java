package com.ncs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.exception.ApplicationException;
import com.ncs.exception.DuplicateRecordException;
import com.ncs.exception.RecordNotFoundException;
import com.ncs.util.JDBCDataSource;

public class RegisterPrintedBookModel {

	private static Logger log = Logger.getLogger(RegisterPrintedBookModel.class);
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
	public long add(RegisterPrintedBookBean bean) throws ApplicationException,
	DuplicateRecordException,RecordNotFoundException{
		Connection conn = null;
	
		long pk = 0;
		if (findByBookNo(bean.getBookId())){
				if (findByBookId(bean.getBookId())) {
					throw new DuplicateRecordException("Book is already exist.");
				}
		}else{
			throw new RecordNotFoundException("Book ID does not exist.");
		}
		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO registerprintedbook VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getBookName());
			pstmt.setString(3, bean.getBookId());
			
			//java.util.Date dt = bean.getDate();

			//String date = sdf.format(dt);
			//System.out.println("date"+date);
			System.out.println("ddd"+bean.getDate());
			pstmt.setDate(4,  new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(5, bean.getMobileno());
			pstmt.setString(6, bean.getEmail());

			pstmt.setString(7, bean.getPassword());
			pstmt.setBoolean(8, false);
			pstmt.setBoolean(9, false);
	
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException(
						"Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public boolean find(RegisterPrintedBookBean bean) throws ApplicationException,
	DuplicateRecordException,RecordNotFoundException{
		boolean flag = false;
		if (findByBookNo(bean.getBookId())){
			if (findByBookId(bean.getBookId())) {
				flag= true;
				throw new DuplicateRecordException("Book is already exist.");
			}
	}else{
		flag= true;
		throw new RecordNotFoundException("Book ID does not exist.");
	}
		return flag;
	
	}
	public List search(RegisterPrintedBookBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	
	public List search(RegisterPrintedBookBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM registerprintedbookview  WHERE 1=1");

		if (bean != null) {

			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL_ID like '" + bean.getEmail()
						+ "%'");
			}
			if (bean.getBookId() != null && bean.getBookId().length() > 0) {
				sql.append(" AND BOOK_ID like '" + bean.getBookId()
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
log.info(sql);
System.out.println(sql); 
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				 bean = new RegisterPrintedBookBean();
				bean.setBookName(rs.getString(1));
				bean.setBookId(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setEmail(rs.getString(4));

				bean.setRlPartI(rs.getBoolean(5));
				bean.setRlPartII(rs.getBoolean(6));
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
	
	public List printedbooklist(String email) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM registerprintedbookview where EMAIL_ID=? and RL_PART_I=? and DATEDIFF(DATE_FORMAT(DATE(NOW()),'%y-%m-%d'),DATE_FORMAT(DATE_OF_PURCHASE,'%y-%m-%d'))>15");
			pstmt.setString(1, email);
			pstmt.setBoolean(2, false);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegisterPrintedBookBean bean = new RegisterPrintedBookBean();
				bean.setBookName(rs.getString(1));
				bean.setBookId(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setEmail(rs.getString(4));

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
	
	public List printedbooklistPartTwo(String email) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM registerprintedbook where EMAIL_ID=? and RL_PART_II=? and DATEDIFF(DATE_FORMAT(DATE(NOW()),'%y-%m-%d'),DATE_FORMAT(DATE_OF_PURCHASE,'%y-%m-%d'))>15");
			pstmt.setString(1, email);
			pstmt.setBoolean(2, false);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegisterPrintedBookBean bean = new RegisterPrintedBookBean();
				bean.setBookName(rs.getString(1));
				bean.setBookId(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setEmail(rs.getString(4));
	
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
	
	public boolean findByBookId(String bookId) throws ApplicationException {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM registerprintedbookview WHERE BOOK_ID=?");
			pstmt.setString(1, bookId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in getting n");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return flag;
	}

	public boolean findByBookNo(String bookNo) throws ApplicationException {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM printed_book WHERE BOOK_NO=?");
			pstmt.setString(1, bookNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in getting n");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return flag;
	}
	
	public boolean findByLogin(String email) throws Exception {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM user WHERE EMAIL_ID=?");
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return flag;
	}
	
	public RegisterPrintedBookBean findByPk(String bookId) throws ApplicationException {
		Connection conn = null;
		RegisterPrintedBookBean bean=null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM registerprintedbookview WHERE BOOK_ID=?");
			pstmt.setString(1, bookId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				 bean = new RegisterPrintedBookBean();
					bean.setBookName(rs.getString(1));
					bean.setBookId(rs.getString(2));
					bean.setDate(rs.getDate(3));
					bean.setEmail(rs.getString(4));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in getting n");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}

}
