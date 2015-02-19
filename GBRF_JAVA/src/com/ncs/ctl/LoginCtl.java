package com.ncs.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.bean.LikesBean;
import com.ncs.model.LikesModel;

public class LoginCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String bookId = request.getParameter("bookId");

		String operation = request.getParameter("operation");

		//get model
		LikesModel model = new LikesModel();

		if ("SignIn".equalsIgnoreCase(operation)) {
			String message = null;

			LikesBean bean = new LikesBean();

			bean.setEmail(login);
			bean.setPassword(password);
			bean.setBookNo(bookId);

			bean = model.authenticate(bean);

			System.out.println(bean.getEmail() + " " + bean.getPassword());

			try {
				if (bookId == "" || bean.getEmail() == null
						|| bean.getPassword() == null) {
					message = "Please Enter valid Login Id/Password & Book Id...!!!";

					request.setAttribute("message", message);
					RequestDispatcher rd = request
							.getRequestDispatcher("login.jsp");
					rd.forward(request, response);

				} else {
					response.sendRedirect("LikesCtl");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}