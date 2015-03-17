package com.ncs.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.bean.ReadLikeAwardPartOneBean;
import com.ncs.bean.ReleaseAndBuyAwardListBean;
import com.ncs.model.BooksModel;
import com.ncs.model.ReadLikeAwardPartOneModel;
import com.ncs.model.ReleaseAndBuyAwardListModel;
import com.ncs.util.DataUtility;
import com.ncs.util.PropertyReader;
import com.ncs.util.ServletUtility;

public class ReleaseAndBuyAwardListCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;
		BooksModel bookmodel = new BooksModel();
		ReleaseAndBuyAwardListModel model = new ReleaseAndBuyAwardListModel();
		List dtoList = null;
		try {
			ReleaseAndBuyAwardListBean bean = new ReleaseAndBuyAwardListBean();
			List list = bookmodel.list();
			request.setAttribute("bookList", list);
			dtoList = model.search(bean, pageNo, pageSize);
			request.setAttribute("dtoList", dtoList);
			if (dtoList == null || dtoList.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			request.setAttribute("dtoList", dtoList);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			RequestDispatcher rd = request
					.getRequestDispatcher("ReleaseAndBuyAwardList.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
				.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		ReleaseAndBuyAwardListModel model = new ReleaseAndBuyAwardListModel();

		ReleaseAndBuyAwardListBean bean = new ReleaseAndBuyAwardListBean();
		BooksModel bookmodel = new BooksModel();
		bean.setBookName(request.getParameter("bookName"));
		List dtoList = null;
		try {
			List bookList = bookmodel.list();
			request.setAttribute("bookList", bookList);
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

			dtoList = model.search(bean, pageNo, pageSize);
			request.setAttribute("dtoList", dtoList);
			if (dtoList == null || dtoList.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			request.setAttribute("dtoList", dtoList);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			RequestDispatcher rd = request
					.getRequestDispatcher("ReleaseAndBuyAwardList.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}