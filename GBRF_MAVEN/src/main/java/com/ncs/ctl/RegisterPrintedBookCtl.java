package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.bean.UserBean;
import com.ncs.model.BooksModel;
import com.ncs.model.RegisterPrintedBookModel;
import com.ncs.util.DataUtility;
import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class RegisterPrintedBookCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			BooksModel model = new BooksModel();
			List list = model.list();
			ServletUtility.setList(list, request);
			ServletUtility
					.forward("RegisterPrintedBook.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String dateofpurchase = request.getParameter("dateofpurchase");
		if (DataValidator.isNull(request.getParameter("bookName"))) {
			request.setAttribute("bookName",
					PropertyReader.getValue("error.require", "Book Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("bookId"))) {
			request.setAttribute("bookId",
					PropertyReader.getValue("error.require", "Book ID"));
			pass = false;
		}

		if (DataValidator.isNull(dateofpurchase)) {
			request.setAttribute("dateofpurchase", PropertyReader.getValue(
					"error.require", "Date Of Purchase"));
			pass = false;
		} else if (!DataValidator.isDate(dateofpurchase)) {
			request.setAttribute("dateofpurchase",
					PropertyReader.getValue("error.date", "Date Of Purchase"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",
					PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}

		return pass;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String operation = request.getParameter("operation");
		// get model
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();

		if ("Save".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				RegisterPrintedBookBean bean = new RegisterPrintedBookBean();
				bean.setBookName(DataUtility.getString(request
						.getParameter("bookName")));
				bean.setBookId(DataUtility.getInt(request
						.getParameter("bookId")));
				bean.setMobileno(DataUtility.getString(request
						.getParameter("mobileNo")));
				bean.setDate(DataUtility.getDate(request.getParameter("dateofpurchase")));
			if((UserBean) session.getAttribute("user")!=null){
				bean.setEmail(((UserBean) session.getAttribute("user"))
						.getEmail());
				bean.setPassword(((UserBean) session.getAttribute("user"))
						.getPassword());
			}else{
				bean.setEmail(DataUtility.getString(request
						.getParameter("email")));
				bean.setPassword(DataUtility.getString(request
						.getParameter("password")));
			}
				try {
					model.add(bean);
					if((UserBean) session.getAttribute("user")!=null){
					ServletUtility.redirect("RegisteredBooksCtl", request, response);
					}else{
						ServletUtility.redirect("LoginCtl", request, response);	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				doGet(request, response);
			}
			}
		}
	

}
