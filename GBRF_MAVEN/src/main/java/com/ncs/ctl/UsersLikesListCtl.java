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
import com.ncs.util.DataUtility;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class UsersLikesListCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get model
		HttpSession session = request.getSession(true);
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;
		LikesModel model = new LikesModel();
		List dtoList = null;
		try {
				String uri = request.getRequestURI();
				LikesBean bean=new LikesBean();
				if (uri.endsWith("MyUsersLikesListCtl")) {
					String email=(session.getAttribute("email")).toString();
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
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;
		
		String op = DataUtility.getString(request.getParameter("operation"));
			LikesModel model = new LikesModel();
			List dtoList = null;
			try {
				String uri = request.getRequestURI();
				LikesBean bean=new LikesBean();
				bean.setBookNo(request.getParameter("bookId"));
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
				if (uri.endsWith("MyUsersLikesListCtl")) {
					String email=(session.getAttribute("email")).toString();
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
		RequestDispatcher rd = request.getRequestDispatcher("UsersLikesList.jsp");
		rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

	}
}