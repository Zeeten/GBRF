package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.bean.UserBean;
import com.ncs.model.LikesModel;
import com.ncs.model.RegisterPrintedBookModel;

public class RegisteredBooksCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		RegisterPrintedBookModel model = new RegisterPrintedBookModel();
		List dtoList = null;
		try {
			String user=((UserBean) session.getAttribute("user"))
			.getEmail();
			dtoList = model.list(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("dtoList", dtoList);
		RequestDispatcher rd = request.getRequestDispatcher("RegisteredBooks.jsp");
		rd.forward(request, response);
	}


}
