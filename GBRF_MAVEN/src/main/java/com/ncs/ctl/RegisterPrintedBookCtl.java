package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.bean.UserBean;
import com.ncs.exception.ApplicationException;
import com.ncs.exception.DuplicateRecordException;
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
			//request.setAttribute("BList", list);
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
/*		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo",
					PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}
*/
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
				bean.setBookId(DataUtility.getString(request
						.getParameter("bookId")));
		
				bean.setDate(DataUtility.getDate(request.getParameter("dateofpurchase")));
			if(session.getAttribute("session")!=null){
				bean.setEmail((session.getAttribute("email").toString()));
				bean.setPassword((session.getAttribute("password").toString()));
				bean.setMobileno(( session.getAttribute("telephone").toString()));
			}else{
				bean.setMobileno(DataUtility.getString(request
						.getParameter("mobileNo")));
				bean.setEmail(DataUtility.getString(request
						.getParameter("email")));
				bean.setPassword(DataUtility.getString(request
						.getParameter("password")));
			}
				try {
			
					if(session.getAttribute("session")!=null){
					model.add(bean);
						ServletUtility.redirect("MyRegisteredBooksCtl", request, response);	
					}else{
						model.add(bean);
						ServletUtility.redirect("LoginCtl", request, response);	
					}
				} 
				catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage(
							"Book Id already exists", request);
					doGet(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				doGet(request, response);
			}
			}if("My Registered Books".equalsIgnoreCase(operation)){
				ServletUtility.redirect("MyRegisteredBooksCtl", request, response);	
			}
		}
	

}
