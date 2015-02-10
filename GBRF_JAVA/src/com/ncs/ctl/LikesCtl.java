package com.ncs.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.bean.LikesBean;
import com.ncs.model.LikesModel;

public class LikesCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("likespage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String like1 = request.getParameter("like1");
		String like2 = request.getParameter("like2");
		String like3 = request.getParameter("like3");
		String operation = request.getParameter("operation");

		// get model
		LikesModel model = new LikesModel();
		if ("Save".equalsIgnoreCase(operation)) {
			LikesBean bean = new LikesBean();
			bean.setLike1(like1);
			bean.setLike2(like2);
			bean.setLike3(like3);
			try {
				model.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("LikesListCtl");
		}
	}
}