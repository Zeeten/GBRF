package com.ncs.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.bean.LikesBean;
import com.ncs.bean.UserBean;
import com.ncs.model.LikesModel;
import com.ncs.util.DataValidator;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class UsersLikesCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("UsersLikes.jsp");
		rd.forward(request, response);
	}

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("like1"))) {
			request.setAttribute("like1",
					PropertyReader.getValue("error.require", "Like 1"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("like2"))) {
			request.setAttribute("like2",
					PropertyReader.getValue("error.require", "Like 2"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("like3"))) {
			request.setAttribute("like3",
					PropertyReader.getValue("error.require", "Like 3"));
			pass = false;
		}

		return pass;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String operation = request.getParameter("operation");
		// get model
		LikesModel model = new LikesModel();

		if ("Save".equalsIgnoreCase(operation)) {
			if (validate(request)) {
				String like1 = request.getParameter("like1");
				String like2 = request.getParameter("like2");
				String like3 = request.getParameter("like3");

				LikesBean bean = new LikesBean();
				bean.setEmail(((UserBean) session.getAttribute("user"))
						.getEmail());
				bean.setBookName((String) session.getAttribute("bookName"));
				bean.setBookNo((String) session.getAttribute("bookNo"));
				bean.setLike1(like1);
				bean.setLike2(like2);
				bean.setLike3(like3);
				try {
					model.add(bean);
					ServletUtility.redirect("UsersLikesThank.jsp", request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				doGet(request, response);
			}
		}
	}
}