package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.bean.LikesBean;
import com.ncs.bean.UserBean;
import com.ncs.model.LikesModel;

public class UsersLikesListCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get model
		HttpSession session = request.getSession(true);
		LikesModel model = new LikesModel();
		List dtoList = null;
		try {
				String uri = request.getRequestURI();
				LikesBean bean=new LikesBean();
				if (uri.endsWith("MyUsersLikesListCtl")) {
					String email=(session.getAttribute("email")).toString();
					bean.setEmail(email);
				}
				dtoList = model.search(bean);
				request.setAttribute("dtoList", dtoList);
				RequestDispatcher rd = request.getRequestDispatcher("UsersLikesList.jsp");
				rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get model
		HttpSession session = request.getSession(true);
			LikesModel model = new LikesModel();
			List dtoList = null;
			try {
				String uri = request.getRequestURI();
				LikesBean bean=new LikesBean();
				bean.setBookNo(request.getParameter("bookId"));
				bean.setBookName(request.getParameter("bookName"));
				bean.setEmail(request.getParameter("emailId"));
				if (uri.endsWith("MyUsersLikesListCtl")) {
					String email=(session.getAttribute("email")).toString();
					bean.setEmail(email);
				}
				dtoList = model.search(bean);
		request.setAttribute("dtoList", dtoList);
		RequestDispatcher rd = request.getRequestDispatcher("UsersLikesList.jsp");
		rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

	}
}