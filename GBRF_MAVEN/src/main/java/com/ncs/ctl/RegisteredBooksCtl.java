package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ncs.bean.RegisterPrintedBookBean;
import com.ncs.bean.UserBean;
import com.ncs.model.RegisterPrintedBookModel;
import com.ncs.util.DataUtility;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class RegisteredBooksCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(RegisteredBooksCtl.class);
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();
		List dtoList = null;
		try {
			String uri = request.getRequestURI();
			RegisterPrintedBookBean bean=new RegisterPrintedBookBean();
			if (uri.endsWith("MyRegisteredBooksCtl")) {

				String email = (session.getAttribute("email")).toString();
				bean.setEmail(email);
			}
			dtoList = model.search(bean, pageNo, pageSize);
			request.setAttribute("dtoList", dtoList);
			if (dtoList == null || dtoList.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			request.setAttribute("dtoList", dtoList);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisteredBooks.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;
		
		String op = DataUtility.getString(request.getParameter("operation"));
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();
		List dtoList = null;
		try {
			String uri = request.getRequestURI();

			RegisterPrintedBookBean bean=new RegisterPrintedBookBean();
			bean.setBookId(request.getParameter("bookId"));
			bean.setBookName(request.getParameter("bookName"));
			bean.setEmail(request.getParameter("emailId"));
			if ("Search".equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
					|| "Previous".equalsIgnoreCase(op)) {

				if ("Search".equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if ("Next".equalsIgnoreCase(op)) {
					pageNo++;
				} else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			}
			if (uri.endsWith("MyRegisteredBooksCtl")) {

				String email = (session.getAttribute("email")).toString();
				bean.setEmail(email);
			}
			dtoList = model.search(bean, pageNo, pageSize);
		request.setAttribute("dtoList", dtoList);
			if (dtoList == null || dtoList.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			request.setAttribute("dtoList", dtoList);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisteredBooks.jsp");
		rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
	}

	}

}
