package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.BooksModel;
import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class WelcomeCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			BooksModel model = new BooksModel();
			List list = model.list();
			ServletUtility.setList(list, request);
			ServletUtility.forward("welcome.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bookName"))) {
			request.setAttribute("bookName",
					PropertyReader.getValue("error.require", "Book Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("bookNo"))) {
			request.setAttribute("bookNo",
					PropertyReader.getValue("error.require", "Book No"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String operation = request.getParameter("operation");

		if ("Go".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				String bookName = request.getParameter("bookName");
				String bookNo = request.getParameter("bookNo");

				session.setAttribute("bookName", bookName);
				session.setAttribute("bookNo", bookNo);
				ServletUtility.redirect("LikesCtl", request, response);
			} else {
				doGet(request, response);
			}
		}
	}

}