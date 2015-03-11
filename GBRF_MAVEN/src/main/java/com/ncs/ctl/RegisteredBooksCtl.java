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

public class RegisteredBooksCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(RegisteredBooksCtl.class);
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();
		List dtoList = null;
		try {
			String uri = request.getRequestURI();
			RegisterPrintedBookBean bean=new RegisterPrintedBookBean();
			if (uri.endsWith("MyRegisteredBooksCtl")) {

				String email = (session.getAttribute("email")).toString();
				bean.setEmail(email);
			}
			dtoList = model.search(bean);
			request.setAttribute("dtoList", dtoList);
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
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();
		List dtoList = null;
		try {
			String uri = request.getRequestURI();
			System.out.println("uri" + uri);
			RegisterPrintedBookBean bean=new RegisterPrintedBookBean();
			bean.setBookId(request.getParameter("bookId"));
			bean.setBookName(request.getParameter("bookName"));
			bean.setEmail(request.getParameter("emailId"));
			if (uri.endsWith("MyRegisteredBooksCtl")) {

				String email = (session.getAttribute("email")).toString();
				bean.setEmail(email);
			}
			dtoList = model.search(bean);
		request.setAttribute("dtoList", dtoList);
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisteredBooks.jsp");
		rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
	}

	}

}
