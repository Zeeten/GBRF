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
import com.ncs.model.RegisterPrintedBookModel;
import com.ncs.model.UserModel;

public class UserListCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserModel model = new UserModel();
		List dtoList = null;
		try {
			UserBean bean=new UserBean();

			dtoList = model.search(bean);
			request.setAttribute("dtoList", dtoList);
			RequestDispatcher rd = request
					.getRequestDispatcher("UserList.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		UserModel model = new UserModel();
		List dtoList = null;
		try {

			UserBean bean=new UserBean();
			bean.setName(request.getParameter("name"));
			bean.setEmail(request.getParameter("emailId"));
			dtoList = model.search(bean);
			request.setAttribute("dtoList", dtoList);
			RequestDispatcher rd = request
					.getRequestDispatcher("UserList.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
